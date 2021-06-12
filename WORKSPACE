load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")


http_archive(
    name = "com_google_googletest",
    urls = ["https://github.com/google/googletest/archive/e2239ee6043f73722e7aa812a459f54a28552929.zip"],
    strip_prefix = "googletest-e2239ee6043f73722e7aa812a459f54a28552929",
)


http_archive(
    name = "rules_cc",
    urls = ["https://github.com/bazelbuild/rules_cc/archive/fb624ff008196216a86e958e98fbd1a2615d7bbe.zip"],
    strip_prefix = "rules_cc-fb624ff008196216a86e958e98fbd1a2615d7bbe",
)


git_repository(
    name = "com_github_gflags_gflags",
    remote = "https://github.com/gflags/gflags.git",
    tag = "v2.2.2"
)


http_archive(
    name = "com_google_absl",
    urls = ["https://github.com/abseil/abseil-cpp/archive/98eb410c93ad059f9bba1bf43f5bb916fc92a5ea.zip"],
    strip_prefix = "abseil-cpp-98eb410c93ad059f9bba1bf43f5bb916fc92a5ea",
    # sha256 = "278e0a071885a22dcd2fd1b5576cc44757299343",
)


new_local_repository(
    name = "sql_parser",
    path = "third_party/sql-parser",
    build_file = "third_party/BUILD.parser",
)
