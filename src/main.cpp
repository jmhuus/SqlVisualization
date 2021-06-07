#include <string>
#include "SQLParser.h"
#include "sql/SQLStatement.h"
#include "sql/SelectStatement.h"
#include "sql/Table.h"
#include "util/sqlhelper.h"
#include "node_manager.h"
#include "node.h"
#include <iostream>
#include <vector>
#include <stdexcept>


int main() {
  // // fromTable->getName()
  // std::string query =
  //   "SELECT name, age, gender, sex, address, phone FROM test";
  
  // fromTable->join
  // std::string query =
  //   "SELECT name, age, gender, sex, address, phone "
  //   "FROM test LEFT JOIN test_2 ON test.id = test_2.id;";

  // fromTable->list
  // std::string query =
  //   "SELECT name, age, gender, sex, address, phone FROM test, test_2;";

  // subquery Select
  // std::string query =
  //   "SELECT name, age, gender, sex, address, phone FROM (SELECT * FROM table2) as T1;";

  // Multiple statements
  // std::string query = "SELECT name, age, gender, sex, address, phone FROM test, test_2; SELECT name, age, gender, sex, address, phone FROM test, test_2;";

  // Insert Statement
  std::string query = "INSERT INTO test (column_1, column_2) VALUES (1, 2);";

  query += " SELECT * FROM test;";

  // Parse SQL
  hsql::SQLParserResult result;
  hsql::SQLParser::parse(query, &result);
  if (!result.isValid()) {
    std::cout << "Error when parsing the SQL string: " << result.errorMsg() << "\n";
  }

  // Begin constructing forest graph of nodes/statements
  NodeManager* node_manager = new NodeManager();
  for (int i = 0; i < result.size(); ++i) {
    const hsql::SQLStatement* stmt = result.getStatement(i);
    node_manager->add_node(new Node(stmt, node_manager));
  }

  // Print nodes from NodeManager
  node_manager->print_all_nodes_info();
  return 0;
}
