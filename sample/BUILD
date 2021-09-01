load("@rules_cc//cc:defs.bzl", "cc_binary")

cc_binary(
    name = "sample",
    srcs = ["sample.cc"],
    data = ["//test:test_sql_many_statements.sql"],
    deps = [
        "//src:node",
        "@sql_parser//:sql-parser",
    ],
)
