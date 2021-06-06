cmake_minimum_required(VERSION 3.16.0)

project(SqlVisualization)

add_executable(${PROJECT_NAME} main.cpp node.cpp node_manager.cpp)

# Link library - absolute
add_subdirectory(sql-parser/src)
target_link_libraries(${PROJECT_NAME} sql_parser)
target_include_directories(${PROJECT_NAME} PRIVATE sql-parser/src)


