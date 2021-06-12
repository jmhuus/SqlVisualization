#include "node.h"
#include "node_manager.h"
#include "src/sql/SQLStatement.h"
#include <string>
#include <set>
#include <deque>

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
    if ((*it)->table_type != -1) continue;  // Node represents a table
    if ((*it)->stmt->type() == node->stmt->type()) {
      count++;
    }
  }
  if (count > 1) count -= 1;

  // Set readable name
  std::string type_string;
  for (auto it = Node::type_name_mapping.begin();
       it != Node::type_name_mapping.end(); ++it) {
    if (node->stmt->type() == it->first) {
      type_string = it->second;
      break;
    }
  }
  
  node->set_name(type_string + "_" + std::to_string(count));
}

std::vector<Node*> NodeManager::get_nodes() {
  return _nodes;
}


void NodeManager::print_all_nodes_info() {
  std::cout << get_all_nodes_info() << "\n";
}


std::string NodeManager::get_all_nodes_info() {
  // Current Node
  std::string info = "\n\nNodes in NodeManager:\n";
  for (auto node_it = _nodes.begin(); node_it != _nodes.end(); ++node_it) {
    info += std::string("  Node: ") + (*node_it)->get_name() + "\n";

    // Print parents
    std::vector<Node*> parents = (*node_it)->get_parent_nodes();
    for (auto parent_it = parents.begin(); parent_it != parents.end(); ++parent_it) {
      info += std::string("    parent: ") + (*parent_it)->get_name() + "\n";
    }

    // Print children
    std::vector<Node*> children = (*node_it)->get_child_nodes();
    for (auto child_it = children.begin(); child_it != children.end(); ++child_it) {
      info +=  std::string("    child: ") + (*child_it)->get_name() + "\n";
    }
  }

  return info;
}

void insert_deque_set(std::set<std::string>* set,
  std::deque<std::string>* que, std::string new_item) {
  if (set->count(new_item) < 1) {
    set->insert(new_item);
    que->push_back(new_item);
  }
}


std::string NodeManager::get_graphviz_digraph() {
  // Current Node
  std::deque<std::string> que;
  std::set<std::string> set;
  insert_deque_set(&set, &que, "digraph sql_visualization {\n");
  insert_deque_set(&set, &que, "  node [shape=box]\n");
  for (auto node_it = _nodes.begin(); node_it != _nodes.end(); ++node_it) {
    // Parent nodes
    std::vector<Node*> parents = (*node_it)->get_parent_nodes();
    for (auto parent_it = parents.begin(); parent_it != parents.end(); ++parent_it) {
      insert_deque_set(&set, &que,
		       "  \""+(*parent_it)->get_name()+"\" -> \"" + (*node_it)->get_name() + "\"\n");
    }

    // Child nodes
    std::vector<Node*> children = (*node_it)->get_child_nodes();
    for (auto child_it = children.begin(); child_it != children.end(); ++child_it) {
      insert_deque_set(&set, &que,
		       "  \""+(*node_it)->get_name()+"\" -> \"" + (*child_it)->get_name() + "\"\n");
    }
  }

  insert_deque_set(&set, &que, "}\n");

  std::string result = "";
  for (auto it = que.begin(); it != que.end(); ++it) {
    result += (*it);
  }

  return result;
}
