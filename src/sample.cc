#include <string>
#include "src/SQLParser.h"
#include "src/sql/SQLStatement.h"
#include "src/sql/SelectStatement.h"
#include "src/sql/Table.h"
#include "src/util/sqlhelper.h"
#include "node_manager.h"
#include "node.h"
#include <iostream>
#include <vector>
#include <stdexcept>
#include <fstream>


int main(int argc, char *argv[]) {
  // Read input file
  std::ifstream file_input("test/test_sql_many_statements.sql");
  if (!file_input.good()) {
    std::cout << "can't find file: " << (file_input.fail() ? "true" : "false") << "\n";
    return 1;
  }
  std::string sql_file = "";
  std::string line;
  while (std::getline(file_input, line)) {
      sql_file += line;
  }

  // Parse SQL
  hsql::SQLParserResult result;
  hsql::SQLParser::parse(sql_file, &result);
  if (!result.isValid()) {
    std::cout << "Error when parsing the SQL string: " << result.errorMsg() << "\n";
  }

  // Begin constructing forest graph of nodes/statements
  NodeManager* node_manager = new NodeManager();
  for (int i = 0; i < (int)result.size(); ++i) {
    const hsql::SQLStatement* stmt = result.getStatement(i);
    node_manager->add_node(new Node(stmt, node_manager));
  }

  // Print nodes from NodeManager
  // std::cout << node_manager->get_all_nodes_info();
  std::cout << node_manager->get_graphviz_digraph();

  delete node_manager;
  return 0;
}
