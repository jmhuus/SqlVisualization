#ifndef NODE_H
#define NODE_H

#include <vector>
#include <map>
#include <string>
#include <iostream>
#include "sql/SQLStatement.h"
#include "sql/SelectStatement.h"
#include "sql/InsertStatement.h"
#include "sql/Table.h"
#include "node_manager.fwd.h"

using hsql::SQLStatement;
using hsql::SelectStatement;
using hsql::StatementType;
using hsql::TableRef;
using hsql::TableRefType;
using hsql::InsertStatement;

/**
 * Node class represents a SQL statement.
 * Each node can have zero or more nodes connected to it. These
 * are called 'parent_node' and 'child_node' which result in a graph
 * of SQL statements querying from and resulting to new
 * representations of data.
 */
class Node {
  std::string _name;
  std::vector<Node*> _parent_nodes;
  std::vector<Node*> _child_nodes;
  NodeManager* _node_manager;

public:
  const SQLStatement* stmt;
  int table_type;
  static const std::map<enum StatementType, std::string> type_name_mapping;

  const std::string& get_blah();
  
  // Generates parent/child nodes from a SQL statement
  // Parent nodes - source data. Either tables or other nested queries
  // Child nodes - resulting data source.
  Node(const SQLStatement* statement, NodeManager* node_manager, const std::string& name);
  Node(TableRefType table_ref_type, NodeManager* node_manager, const std::string& name);
  Node(const SQLStatement* statement, NodeManager* node_manager);
  void add_parent_node(Node* node);
  void add_child_node(Node* node);
  std::vector<Node*> get_parent_nodes();
  std::vector<Node*> get_child_nodes();
  void set_name(const std::string& name);
  std::string get_name();
  StatementType get_type();
  // friend bool operator== (Node& lhs, Node& rhs) { }

  // Use on any SQLStatement type
  void init_parent_nodes(const SQLStatement* stmt);
  void init_child_nodes(const SQLStatement* stmt);

  // Statement-specific handlers
  void search_select_from_table(const SelectStatement* stmt);
};

#endif // NODE_H
