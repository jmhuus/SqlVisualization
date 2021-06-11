file(GLOB_RECURSE SOURCES *.cpp *.h)

add_executable(${CMAKE_PROJECT_NAME} ${SOURCES})
target_link_libraries(${CMAKE_PROJECT_NAME} LINK_PUBLIC sql_parser)
target_include_directories(${CMAKE_PROJECT_NAME} PUBLIC ../lib/sql-parser/src)

add_library(${CMAKE_PROJECT_NAME}_testing ${SOURCES})
target_link_libraries(${CMAKE_PROJECT_NAME}_testing LINK_PUBLIC sql_parser)
target_include_directories(${CMAKE_PROJECT_NAME}_testing PUBLIC ../lib/sql-parser/src)
