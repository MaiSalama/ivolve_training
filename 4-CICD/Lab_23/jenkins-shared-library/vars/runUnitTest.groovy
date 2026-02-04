def call(String appDir) {
    dir(appDir) {
        sh 'mvn test'
    }
}