def call(String appDir) {
    dir(appDir) {
        sh '''
            apt-get update
            apt-get install -y maven
            mvn test
        '''
    }
}