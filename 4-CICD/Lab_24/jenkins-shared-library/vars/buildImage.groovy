def call(String appDir, String imageName, String imageTag) {
    dir(appDir) {
        sh "docker build -t ${imageName}:${imageTag} ."
    }
}