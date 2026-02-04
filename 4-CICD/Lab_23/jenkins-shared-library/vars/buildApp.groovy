def call(String appDir) {
    dir(appDir) {
        sh 'mvn package -DskipTests'
    }
}