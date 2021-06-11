#include <gtest/gtest.h>
#include "SQLParser.h"
#include "node.h"
#include "node_manager.h"
#include "sql/SQLStatement.h"
#include "sql/SelectStatement.h"
#include "sql/Table.h"
#include "sql/InsertStatement.h"
#include <string>


class NodeManagerTest : public ::testing::Test {
protected:
  NodeManager* node_manager;
  hsql::SQLParserResult parse_result;
  
  void SetUp() override {
    node_manager = new NodeManager();

    // Initialize test SQLStatements
    std::string query = "SELECT * FROM table1; SELECT * FROM table2";
    hsql::SQLParser::parse(query, &parse_result);
    if (!parse_result.isValid()) {
      std::cout << "Error when parsing the SQL string: " << parse_result.errorMsg() << "\n";
    }
    const hsql::SQLStatement* stmt_1 = parse_result.getStatement(0);
    node_manager->add_node(new Node(stmt_1, node_manager));
    const hsql::SQLStatement* stmt_2 = parse_result.getStatement(1);
    node_manager->add_node(new Node(stmt_2, node_manager));
  }
  
  void TearDown() override { delete node_manager; }
};


TEST_F(NodeManagerTest, TestNodeReferencesSize) {
  // NodeManager has correct number of nodes
  ASSERT_NE(node_manager, nullptr);
  EXPECT_EQ(node_manager->get_nodes().size(), (unsigned long) 4);
}


TEST_F(NodeManagerTest, TestGetNode) {
  // NodeManager has references to two table nodes
  Node* node_3 = node_manager->get_node("table1");
  ASSERT_NE(node_3, nullptr);
  EXPECT_EQ(node_3->table_type, hsql::kTableName);
  Node* node_4 = node_manager->get_node("table2");
  ASSERT_NE(node_4, nullptr);
  EXPECT_EQ(node_4->table_type, hsql::kTableName);

  // NodeManager has references to two SELECT nodes
  Node* node_1 = node_manager->get_node("Select_0");
  ASSERT_NE(node_1, nullptr);
  ASSERT_NE(node_1->stmt, nullptr);
  EXPECT_EQ(node_1->stmt->type(), hsql::kStmtSelect);
  Node* node_2 = node_manager->get_node("Select_1");
  ASSERT_NE(node_2, nullptr);
  ASSERT_NE(node_2->stmt, nullptr);
  EXPECT_EQ(node_2->stmt->type(), hsql::kStmtSelect);
}


TEST_F(NodeManagerTest, TestAddNode) {
  // NodeManager has 4 nodes
  ASSERT_NE(node_manager, nullptr);
  EXPECT_EQ(node_manager->get_nodes().size(), (unsigned long) 4);
  
  const hsql::SQLStatement* stmt_1 = parse_result.getStatement(0);
  node_manager->add_node(new Node(stmt_1, node_manager));

  // NodeManager has 5 nodes
  ASSERT_NE(node_manager, nullptr);
  EXPECT_EQ(node_manager->get_nodes().size(), (unsigned long) 5);
}


TEST_F(NodeManagerTest, TestSetNodeName) {
  const hsql::SQLStatement* stmt_1 = parse_result.getStatement(0);
  Node* test_node = new Node(stmt_1, node_manager, "");
  node_manager->add_node(test_node);

  // Has no name
  EXPECT_EQ(test_node->get_name(), "");

  // Set name
  node_manager->set_node_name(test_node);
  EXPECT_EQ(test_node->get_name(), "Select_2");

  delete test_node;
}


TEST_F(NodeManagerTest, TestGetNodes) {
  std::vector<Node*> nodes = node_manager->get_nodes();
  for (auto it = nodes.begin(); it != nodes.end(); ++it) {
    ASSERT_NE((*it), nullptr);
    EXPECT_GT((int)(*it)->get_name().length(), 0);
  }
}


TEST_F(NodeManagerTest, TestPrintAllNodesInfo) {
  EXPECT_GT((int)node_manager->get_all_nodes_info().size(), 0);
}


/**
 * NodeTest class represents the node class as declared in Node.h.
 */
class NodeTest : public ::testing::Test {
protected:
  hsql::SQLParserResult parse_result;
  
  void SetUp() override {
    // Initialize test SQLStatements
    std::string query = "SELECT * FROM table1; SELECT * FROM table2";
    hsql::SQLParser::parse(query, &parse_result);
    if (!parse_result.isValid()) {
      std::cout << "Error when parsing the SQL string: " << parse_result.errorMsg() << "\n";
    }
  }  
  void TearDown() {
		 
  }
};


TEST_F(NodeTest, TestNodeConstructors) {
  NodeManager* node_manager = new NodeManager();
  const hsql::SQLStatement* stmt = parse_result.getStatement(0);
  Node* node_1;

  // First constructor
  node_1 = new Node(stmt, node_manager, "node_1 name");
  ASSERT_NE(node_1, nullptr);
  const SelectStatement* select_stmt_1 = (const SelectStatement*) node_1->stmt;
  EXPECT_NE(select_stmt_1, nullptr);
  EXPECT_EQ(node_1->get_name(), "node_1 name");
  delete node_1;

  // Second constructor
  node_1 = new Node(hsql::kTableName, node_manager, "node_1 name");
  ASSERT_NE(node_1, nullptr);
  int table_type = node_1->table_type;
  EXPECT_EQ(table_type, hsql::kTableName);
  EXPECT_EQ(node_1->get_name(), "node_1 name");

  // Third constructor
  node_1 = new Node(stmt, node_manager);
  ASSERT_NE(node_1, nullptr);
  const SelectStatement* select_stmt_2 = (const SelectStatement*) node_1->stmt;
  EXPECT_NE(select_stmt_2, nullptr);
  EXPECT_EQ(node_1->get_name(), "Select_0");

  delete node_manager;
  delete node_1;
}


TEST_F(NodeTest, TestNodeAddParentNode) {
  NodeManager node_manager;
  const hsql::SQLStatement* stmt_1 = parse_result.getStatement(0);
  const hsql::SQLStatement* stmt_2 = parse_result.getStatement(1);
  Node node_1(stmt_1, &node_manager);
  node_manager.add_node(&node_1);
  Node node_2(stmt_2, &node_manager);
  node_manager.add_node(&node_2);

  // Add node_2 as parent to node_1
  node_1.add_parent_node(&node_2);
  std::vector<Node*> parent_nodes = node_1.get_parent_nodes();
  for (auto it = parent_nodes.begin(); it != parent_nodes.end(); ++it) {
    if ((*it)->stmt != nullptr) {
      EXPECT_EQ((*it)->get_name(), "Select_1");
    }
  }
}


TEST_F(NodeTest, TestNodeAddChildNode) {
  NodeManager node_manager;
  const hsql::SQLStatement* stmt_1 = parse_result.getStatement(0);
  const hsql::SQLStatement* stmt_2 = parse_result.getStatement(1);
  Node node_1(stmt_1, &node_manager);
  node_manager.add_node(&node_1);
  Node node_2(stmt_2, &node_manager);
  node_manager.add_node(&node_2);

  // Add node_2 as parent to node_1
  node_1.add_child_node(&node_2);
  std::vector<Node*> child_nodes = node_1.get_child_nodes();
  for (auto it = child_nodes.begin(); it != child_nodes.end(); ++it) {
    if ((*it)->stmt != nullptr) {
      EXPECT_EQ((*it)->get_name(), "Select_1");
    }
  }
}


TEST_F(NodeTest, TestNodeSetName) {
  NodeManager node_manager;
  const hsql::SQLStatement* stmt = parse_result.getStatement(0);
  Node node(stmt, &node_manager);
  node.set_name("hello world");
  EXPECT_EQ(node.get_name(), "hello world");
}


TEST_F(NodeTest, TestNodeStatementType) {
  NodeManager node_manager;
  const hsql::SQLStatement* stmt = parse_result.getStatement(0);
  Node node(stmt, &node_manager);
  EXPECT_EQ(hsql::kStmtSelect, node.get_type());
}
