def call(String appDir, String imageName, String imageTag, String namespace) {

    if (!namespace?.trim()) {
        echo "❌ K8S_NAMESPACE is EMPTY"
        error "K8S_NAMESPACE is empty. Check Detect Environment stage."
    }

    dir(appDir) {
        withEnv(["K8S_NAMESPACE=${namespace}"]) {
            sh """
              echo "Deploying to namespace: \$K8S_NAMESPACE"

              # sanity checks
              kubectl version --client
              kubectl cluster-info

              # update image tag
              sed -i 's|image:.*|image: ${imageName}:${imageTag}|g' deployment.yaml

              echo "Updated deployment.yaml:"
              grep image deployment.yaml

              # apply and wait
              kubectl apply -f deployment.yaml -n \$K8S_NAMESPACE
              kubectl rollout status deployment/jenkins-app -n \$K8S_NAMESPACE --timeout=120s
            """
        }
    }
}
