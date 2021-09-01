load("@rules_cc//cc:defs.bzl", "cc_binary")

cc_library(
    name = "node",
    srcs = [
        "node.cc",
        "node_manager.cc",
    ],
    hdrs = [
        "node.h",
        "node.fwd.h",
        "node_manager.h",
        "node_manager.fwd.h"
    ],
    deps = [
        "@sql_parser//:sql-parser",
        "@com_github_gflags_gflags//:gflags"
    ],
    copts = [
        "-std=gnu++14",
        "-std=c++14",
    ],
    visibility = ["//visibility:public"]
)


cc_binary(
    name = "sql_visualization",
    srcs = ["sql_visualization.cc"],
    deps = [
        ":node",
        "@sql_parser//:sql-parser",
        "@com_google_absl//absl/flags:flag",
        "@com_google_absl//absl/flags:parse"
    ],
)
