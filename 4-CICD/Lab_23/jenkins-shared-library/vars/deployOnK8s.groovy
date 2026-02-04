def call(String appDir) {
    dir(appDir) {
        echo 'Deploy skipped (local cluster not reachable from Jenkins agent)'
    }
}