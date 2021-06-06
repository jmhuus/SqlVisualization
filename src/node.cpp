#include <vector>
#include "node.h"
#include "node_manager.h"
#include <iostream>


using hsql::SQLStatement;
using hsql::SelectStatement;
using hsql::StatementType;
using hsql::TableRef;
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

Node::Node(const std::string& name, NodeManager* node_manager) {
  _name = name;
  _node_manager = node_manager;
}

Node::Node(const SQLStatement* statement, NodeManager* node_manager) {
  // Set up current node
  _node_manager = node_manager;
  stmt = statement;
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

void Node::get_select_from_table(TableRef* from_table) {
  // Determine how the from table syntax
  switch (from_table->type) {
    
  case hsql::kTableName: {
    if (!_node_manager->node_exists(from_table->getName())) {
      Node* new_parent_node = new Node(from_table->getName(), _node_manager);
      new_parent_node->add_child_node(this);
      add_parent_node(new_parent_node);
      _node_manager->add_node(new_parent_node);
    }
    break;
  }
    
  case hsql::kTableSelect: {
    // Recursively fetch the from tables from the subquery
    get_select_from_table(from_table->select->fromTable);
    break;
  }
    
  case hsql::kTableJoin: {
    // Fetch joined tables
    TableRef* left_table = from_table->join->left;
    TableRef* right_table = from_table->join->right;

    // Add left table
    if (!_node_manager->node_exists(left_table->getName())) {
      Node* new_parent_node = new Node(left_table->getName(), _node_manager);
      new_parent_node->add_child_node(this);
      add_parent_node(new_parent_node);
      _node_manager->add_node(new_parent_node);
    }

    // Add right table
    // Joined data source is a select query
    if (right_table->type == hsql::kTableSelect) {
      get_select_from_table(right_table->select->fromTable);
    }

    // Joined data source is a table
    if (right_table->type == hsql::kTableName) {
      if (!_node_manager->node_exists(right_table->getName())) {
	Node* new_parent_node = new Node(right_table->getName(), _node_manager);
	new_parent_node->add_child_node(this);
	add_parent_node(new_parent_node);
	_node_manager->add_node(new_parent_node);
      }
    }
    break;
  }
  case hsql::kTableCrossProduct: {
    std::vector<TableRef*> from_tables = *(from_table->list);
    for (auto it = from_tables.begin(); it != from_tables.end(); ++it) {
      if (!_node_manager->node_exists((*it)->getName())) {
	Node* new_parent_node = new Node((*it)->getName(), _node_manager);
	new_parent_node->add_child_node(this);
	add_parent_node(new_parent_node);
	_node_manager->add_node(new_parent_node);
      }
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
    const SelectStatement* select_stmt = (const SelectStatement*) stmt;
    get_select_from_table(select_stmt->fromTable);
    break;
  }
  case hsql::kStmtInsert:
    std::cout << "kStmtInsert" << "\n";
    break;
  case hsql::kStmtCreate:
    std::cout << "kStmtCreate" << "\n";
    break;
  case hsql::kStmtImport:
    std::cout << "kStmtImport" << "\n";
    break;
  case hsql::kStmtExport:
    std::cout << "kStmtExport" << "\n";
    break;
  case hsql::kStmtTransaction:
    std::cout << "kStmtTransaction" << "\n";
    break;
  default:
    break;
  }
}

void Node::init_child_nodes(const SQLStatement* stmt) {
  std::cout << "node.cpp Node::get_child_nodes() NOT IMPLEMENTED" << "\n";
  return;
}
