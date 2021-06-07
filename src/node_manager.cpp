#include "node.h"
#include "node_manager.h"
#include "sql/SQLStatement.h"
#include <string>

void NodeManager::add_node(Node* node) {
  _nodes.push_back(node);
}

bool NodeManager::node_exists(const std::string& name) {
  for (auto it = _nodes.begin(); it != _nodes.end(); ++it) {
    if (name == (*it)->get_name()) {
      return true;
    }
  }
  return false;
}

Node* NodeManager::get_node(const std::string& node_name) {
  for (auto node_it = _nodes.begin(); node_it != _nodes.end(); ++node_it) {
    if ((*node_it)->get_name() == node_name) {
      return *node_it;
    }
  }

  throw NodeManagerError(std::string(
    "NodeManager::get_node() cannot find node with name '" +
    node_name + "'.").c_str());
}

void NodeManager::set_node_name(Node* node) {
  int count = 0;
  for (auto it = _nodes.begin(); it != _nodes.end(); ++it) {
    if ((*it)->stmt->type() == node->stmt->type()) {
      count++;
    }
  }

  // Set readable name
  std::string type_string;
  for (auto it = Node::type_name_mapping.begin();
       it != Node::type_name_mapping.end(); ++it) {
    if (node->stmt->type() == it->first) {
      type_string = it->second;
    }
  }
  
  node->set_name(type_string + "_" + std::to_string(count));
}

std::vector<Node*> NodeManager::get_nodes() {
  return _nodes;
}


void NodeManager::print_all_nodes_info() {
  // Current Node
  std::cout << "\n\nNodes in NodeManager:" << "\n";
  for (auto node_it = _nodes.begin(); node_it != _nodes.end(); ++node_it) {
    std::cout << "  Node: " << (*node_it)->get_name() << "\n";

    // Print parents
    std::vector<Node*> parents = (*node_it)->get_parent_nodes();
    for (auto parent_it = parents.begin(); parent_it != parents.end(); ++parent_it) {
      std::cout << "    parent: " << (*parent_it)->get_name() << "\n";
    }

    // Print children
    std::vector<Node*> children = (*node_it)->get_child_nodes();
    for (auto child_it = children.begin(); child_it != children.end(); ++child_it) {
      std::cout << "    child: " << (*child_it)->get_name() << "\n";
    }
  }
}
