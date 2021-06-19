#include "absl/flags/flag.h"
#include "absl/flags/parse.h"
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


// Set up flags
// DEFINE_string(input, "", "Fully qualified path to .sql file.");
// DEFINE_string(output_file, "./", "Relative path to .sql e.fil");
ABSL_FLAG(std::string, input_file, "", "Fully qualified path to .sql file.");
ABSL_FLAG(std::string, query, "", "Directly provided raw SQL query text. (Required if not input_file)");
ABSL_FLAG(std::string, out, "", "Optional digraph output file. If "
	  "not specified, digraph to standard out. (optional)");


int main(int argc, char *argv[]) {
  // gflags::ParseCommandLineFlags(&argc, &argv, true);
  // gflags::MarkFlagAsRequired("input");
  absl::ParseCommandLine(argc, argv);

  std::string query = "";
  if (absl::GetFlag(FLAGS_input_file) != "") {
    // Read input file
    std::ifstream file_input(absl::GetFlag(FLAGS_input_file));
    if (!file_input.good()) {
      std::cout << "can't find file: " << (file_input.fail() ? "true" : "false") << "\n";
      return 1;
    }
    std::string line;
    while (std::getline(file_input, line)) {
      query += line;
    }
  } else if (absl::GetFlag(FLAGS_query) != "") {
    query = absl::GetFlag(FLAGS_query);
  }

  // Parse SQL
  hsql::SQLParserResult result;
  hsql::SQLParser::parse(query, &result);
  if (!result.isValid()) {
    std::cout << "Error when parsing the SQL string: " << result.errorMsg() << "\n";
  }

  // Begin constructing forest graph of nodes/statements
  NodeManager* node_manager = new NodeManager();
  for (int i = 0; i < (int)result.size(); ++i) {
    const hsql::SQLStatement* stmt = result.getStatement(i);
    node_manager->add_node(new Node(stmt, node_manager));
  }

  // Print or write digraph
  if (absl::GetFlag(FLAGS_out) != "") {
    std::ofstream file_output(absl::GetFlag(FLAGS_out), std::ofstream::out);
    file_output << node_manager->get_graphviz_digraph();
  } else {
    std::cout << node_manager->get_graphviz_digraph();
  }
  

  delete node_manager;
  return 0;
}
