def call(String imageName, String imageTag) {
    sh """
    echo "Scanning image ${imageName}:${imageTag}"
    docker images | grep ${imageName}
    """
}