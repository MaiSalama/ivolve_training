def call(String appDir, String imageName, String imageTag, String namespace) {
    dir(appDir) {
        sh '''
          echo "Deploying to namespace: ${namespace}"

            # sanity checks
            kubectl version --client
            kubectl cluster-info

            # update image tag in deployment
            sed -i "s|image:.*|image: ${IMAGE_NAME}:${IMAGE_TAG}|g" deployment.yaml

            echo "Updated deployment.yaml:"
            grep image deployment.yaml

            # apply and wait
            kubectl apply -f deployment.yaml -n ${namespace}
            kubectl rollout status deployment/jenkins-app -n ${namespace} --timeout=120s
        '''
    }
}
