enable_testing()

add_executable(${CMAKE_PROJECT_NAME}_test test.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}_test gtest_main)
target_link_libraries(${CMAKE_PROJECT_NAME}_test ${CMAKE_PROJECT_NAME}_testing)
target_link_libraries(${CMAKE_PROJECT_NAME}_test sql_parser)
target_include_directories(${CMAKE_PROJECT_NAME}_test PRIVATE ../src)
target_include_directories(${CMAKE_PROJECT_NAME}_test PRIVATE ../lib/sql-parser/src)


include(GoogleTest)
gtest_discover_tests(${CMAKE_PROJECT_NAME}_test)
