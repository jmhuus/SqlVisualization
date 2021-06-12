#include <vector>
#include "node.h"
#include "node_manager.h"
#include "src/sql/SQLStatement.h"
#include "src/sql/SelectStatement.h"
#include "src/sql/Table.h"
#include "src/sql/InsertStatement.h"
#include <iostream>


using hsql::SQLStatement;
using hsql::SelectStatement;
using hsql::StatementType;
using hsql::TableRef;
using hsql::TableRefType;
using hsql::InsertStatement;

const std::map<enum StatementType, std::string>
Node::type_name_mapping = {
			   { hsql::kStmtSelect, "Select" },
			   { hsql::kStmtImport, "Import" },
			   { hsql::kStmtInsert, "Insert" },
			   { hsql::kStmtUpdate, "Update" },
			   { hsql::kStmtDelete, "Delete" },
			   { hsql::kStmtCreate, "Create" },
			   { hsql::kStmtDrop, "Drop" },
			   { hsql::kStmtPrepare, "Prepare" },
			   { hsql::kStmtExecute, "Execute" },
			   { hsql::kStmtExport, "Export" },
			   { hsql::kStmtRename, "Rename" },
			   { hsql::kStmtAlter, "Alter" },
			   { hsql::kStmtShow, "Show" },
			   { hsql::kStmtTransaction, "Transaction" }
};

Node::Node(const SQLStatement* statement, NodeManager* node_manager,
	   const std::string& name) {
  // Node represents a SQL statement, not a table
  stmt = statement;
  table_type = -1;
  _name = name;
  _node_manager = node_manager;
}

Node::Node(TableRefType table_ref_type, NodeManager* node_manager,
	   const std::string& name) {
  // Node represents a table, not a SQL statement
  table_type = table_ref_type;
  stmt = nullptr;
  _name = name;
  _node_manager = node_manager;
}

Node::Node(const SQLStatement* statement, NodeManager* node_manager) {
  // Set up current node
  _node_manager = node_manager;
  stmt = statement;
  table_type = -1;
  // Unique to this constructor. Name needs to be generated.
  node_manager->set_node_name(this);
  
  
  // Search for parent and child nodes
  init_parent_nodes(stmt);
  init_child_nodes(stmt);
}

void Node::add_parent_node(Node* node) {
  _parent_nodes.push_back(node);
}
  
void Node::add_child_node(Node* node) {
  _child_nodes.push_back(node);
}

std::vector<Node*> Node::get_parent_nodes() {
  return _parent_nodes;
}

std::vector<Node*> Node::get_child_nodes() {
  return _child_nodes;
}

void Node::set_name(const std::string& name) {
  _name = name;
}

std::string Node::get_name() {
  return _name;
}

StatementType Node::get_type() {
  return stmt->type();
}

void Node::search_select_from_table(const SelectStatement* stmt) {
  TableRef* from_table = stmt->fromTable;
  
  // Determine how the from table syntax
  switch (from_table->type) {
  case hsql::kTableName: {
    Node* new_parent_node;
    if (!_node_manager->node_exists(from_table->getName())) {
       new_parent_node = new Node(from_table->type, _node_manager, from_table->getName());
       _node_manager->add_node(new_parent_node);
    } else {
      try {
	new_parent_node = _node_manager->get_node(from_table->getName());
      } catch (NodeManagerError& nme) {
	std::cout << "Error: " << nme.what() << "\n";
      }
    }
    new_parent_node->add_child_node(this);
    add_parent_node(new_parent_node);
    break;
  }
    
  case hsql::kTableSelect: {
    // Recursively fetch the from tables from the subquery
    search_select_from_table(from_table->select);
    break;
  }
    
  case hsql::kTableJoin: {
    // Fetch joined tables
    TableRef* left_table = from_table->join->left;
    TableRef* right_table = from_table->join->right;

    // Add left table
    Node* new_parent_node = nullptr;
    if (!_node_manager->node_exists(left_table->getName())) {
      new_parent_node = new Node(left_table->type, _node_manager, left_table->getName());
      _node_manager->add_node(new_parent_node);
    } else {
      try {
	new_parent_node = _node_manager->get_node(left_table->getName());
      } catch (NodeManagerError& nme) {
	std::cout << "Error: " << nme.what() << "\n";
      }
    }
    new_parent_node->add_child_node(this);
    add_parent_node(new_parent_node);

    // Add right table
    // Recurse: joined data source is a select query
    if (right_table->type == hsql::kTableSelect) {
      search_select_from_table(right_table->select);
    }

    // Joined data source is a table
    if (right_table->type == hsql::kTableName) {
      Node* new_parent_node = nullptr;
      if (!_node_manager->node_exists(right_table->getName())) {
	new_parent_node = new Node(right_table->type, _node_manager, right_table->getName());
	_node_manager->add_node(new_parent_node);
      } else {
	try {
	  new_parent_node = _node_manager->get_node(right_table->getName());
	} catch (NodeManagerError& nme) {
	  std::cout << "Error: " << nme.what() << "\n";
	}
      }
      new_parent_node->add_child_node(this);
      add_parent_node(new_parent_node);
    }
    break;
  }
  case hsql::kTableCrossProduct: {
    std::vector<TableRef*> from_tables = *(from_table->list);
    for (auto it = from_tables.begin(); it != from_tables.end(); ++it) {
      Node* new_parent_node = nullptr;
      if (!_node_manager->node_exists((*it)->getName())) {
	new_parent_node = new Node((*it)->type, _node_manager, (*it)->getName());
	_node_manager->add_node(new_parent_node);
      } else {
	try {
	  new_parent_node = _node_manager->get_node((*it)->getName());
	} catch (NodeManagerError& nme) {
	  std::cout << "Error: " << nme.what() << "\n";
	}
      }
      new_parent_node->add_child_node(this);
      add_parent_node(new_parent_node);
    }
    break;
  }
default:
    break;
  }
}

void Node::init_parent_nodes(const SQLStatement* stmt) {
  std::vector<Node*> nodes; 
    
  switch (stmt->type()) {
  case hsql::kStmtSelect: {
    search_select_from_table((const SelectStatement*) stmt);
    break;
  }
  case hsql::kStmtInsert:
    break;
  case hsql::kStmtCreate:
    break;
  case hsql::kStmtImport:
    break;
  case hsql::kStmtExport:
    break;
  case hsql::kStmtTransaction:
    break;
  default:
    break;
  }
}

void Node::init_child_nodes(const SQLStatement* stmt) {
  switch (stmt->type()) {
  case hsql::kStmtInsert: {
    const InsertStatement* insert_stmt = (const InsertStatement*) stmt;
    Node* new_child_node = nullptr;
    if (!_node_manager->node_exists(insert_stmt->tableName)) {
      new_child_node = new Node(stmt, _node_manager, insert_stmt->tableName);
      _node_manager->add_node(new_child_node);
    } else {
      new_child_node = _node_manager->get_node(insert_stmt->tableName);
    }
    new_child_node->add_parent_node(this);
    add_child_node(new_child_node);
    break;
  }
  case hsql::kStmtCreate: {
    break;
  }
  case hsql::kStmtImport: {
    break;
  }
  case hsql::kStmtExport: {
    break;
  }
  case hsql::kStmtTransaction: {
    break;
  }
  default: {
    break;
  }
  }
  return;
}
