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


int main(int argc, char *argv[]) {

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
