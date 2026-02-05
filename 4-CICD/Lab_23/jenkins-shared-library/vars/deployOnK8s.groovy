def call(String appDir) {
    dir(appDir) {
        sh '''
          echo "Deploying application to Kubernetes..."

          kubectl version --client
          kubectl cluster-info

          kubectl apply -f deployment.yaml
          kubectl rollout status deployment/jenkins-app
        '''
    }
}