add_library(sql_parser SQLParser.cpp SQLParserResult.cpp util/sqlhelper.cpp sql/Expr.cpp sql/PrepareStatement.cpp sql/SQLStatement.cpp sql/statements.cpp parser/bison_parser.cpp parser/conflict_test.cpp parser/flex_lexer.cpp)

target_include_directories(sql_parser PRIVATE ${CMAKE_CURRENT_SOURCE_DIR} parser sql)
