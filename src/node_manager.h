#ifndef NODE_MANAGER_H
#define NODE_MANAGER_H

#include <vector>
#include <string>
#include <exception>
#include "node.fwd.h"
#include "sql/SQLStatement.h"
#include "sql/SelectStatement.h"

/**
 * NodeManager class contains references to all Nodes, defined in 
 * node.h. This manager class is required during node initialization
 * and connecting forest graph edges.
 * 
 * In order to visualize SQL statements, all nodes which are hanging
 * or interconnected must be available in NodeManager.
 * 
 * NodeManager can return references 
 */
class NodeManager {
  std::vector<Node*> _nodes;
 public:
  void add_node(Node* node);
  Node* get_node(const std::string& node_name);
  bool node_exists(const std::string& name);
  void set_node_name(Node* const node);
  std::vector<Node*> get_nodes();
  void print_all_nodes_info();
};

/**
 * NodeManagerError class represents errors that occur when using
 * NodeManager class.
 */
class NodeManagerError: std::exception {
  const char* msg;
public:
  NodeManagerError(const char* s) noexcept(true) : msg(s) { }
  const char* what() const noexcept(true) { return msg; };
};

#endif // NODE_MANAGER_H
