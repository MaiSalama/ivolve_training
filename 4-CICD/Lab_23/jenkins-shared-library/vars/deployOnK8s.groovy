def call(String appDir) {
    dir(appDir) {
        sh '''
          echo "Deploying application to Kubernetes..."

            # sanity checks
            kubectl version --client
            kubectl cluster-info

            # update image tag in deployment
            sed -i "s|image:.*|image: ${IMAGE_NAME}:${IMAGE_TAG}|g" deployment.yaml

            echo "Updated deployment.yaml:"
            grep image deployment.yaml

            # apply and wait
            kubectl apply -f deployment.yaml
            kubectl rollout status deployment/jenkins-app --timeout=120s
        '''
    }
}