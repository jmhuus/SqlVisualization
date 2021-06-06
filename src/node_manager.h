#ifndef NODE_MANAGER_H
#define NODE_MANAGER_H

#include <vector>
#include <string>
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
  bool node_exists(const std::string& name);
  void set_node_name(Node* const node);
  std::vector<Node*> get_nodes();
  void print_all_nodes_info();
};

#endif // NODE_MANAGER_H
