SQL Visualization
=====================
Enable developers to visualize query script dependencies. With SQL Visualization you can see which tables, joins, and subqueries are providing data by leveraging Graphviz node visualization engine. Also, additional SQL statement coverage for temp tables and DROP statements that exist in a script will be covered as well. The simple goal here is to provide better documentation, insight while refactoring monolithic SQL scripts, and continued maintainability.

## Installation steps
1. Install [Bazel](https://docs.bazel.build/versions/main/install.html) on your machine.
2. Install SQL Visualization `bazel install ...TODO(jordanhuus)`
3. Add binary to PATH `PATH=$PATH:/path/to/install_file...TODO(jordanhuus)`

## Usage
* (Ubuntu) Invoke from the command line: `xdot <(sql_visualization --input_file="/full/path/to/SQL/file...)`
* Run the example script: `sql_visualization --example`

## Developer Setup
* Run the sample: `bazel run src:sample`
  * This will output the digraph data
```
digraph sql_visualization {
  node [shape=box]
  "Insert_0" -> "test"
  "test" -> "Select_0"
  "test" -> "Select_1"
  "test" -> "Select_2"
  "test" -> "Select_3"
  "test" -> "Select_4"
  "test_2" -> "Select_0"
  "test_2" -> "Select_1"
  "table2" -> "Select_1"
}
```
* Run the sample: `xdot <(bazel run src:sample)`
  * This will open Graphviz's visualizer
  * (Graphviz output)
  * (ONLY if `xdot` command is available)
* Test SQL Visualization: `bazel run test:sql_visualization_test`

## Debugging Options
* Build binary with extra debug flag `bazel build src:sql_visualization --compilation_mode=dbg`
* Emacs GNU Debugger
  1. `M-x gdb`
  2. `set directories /home/username/path/to/project/`
  3. `break main`
  4. `run`
