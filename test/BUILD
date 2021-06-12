load("@rules_cc//cc:defs.bzl", "cc_test")

cc_test(
    name = "sql_visualization_test",
    size = "small",
    srcs = ["test.cc"],
    data = glob(["*.sql"]),
    deps = [
        "//src:node",
        "@com_google_googletest//:gtest_main",
    ]
)


exports_files(
    glob(["*.sql"])
)
