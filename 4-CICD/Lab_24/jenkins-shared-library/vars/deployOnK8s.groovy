def call(String appDir, String imageName, String imageTag, String namespace) {

    // HARD FAIL early (Groovy-side)
    if (namespace == null || namespace.trim().isEmpty()) {
        error "❌ deployOnK8s: namespace is EMPTY (check Jenkinsfile Detect Environment stage)"
    }

    echo "✅ deployOnK8s received namespace = '${namespace}'"

    dir(appDir) {

        sh """
          set -e

          echo "🚀 Deploying to namespace: ${namespace}"

          # Sanity checks
          kubectl version --client
          kubectl cluster-info

          # Ensure namespace exists
          kubectl get namespace ${namespace} >/dev/null 2>&1 \
            || kubectl create namespace ${namespace}

          # Update image tag
          sed -i 's|image:.*|image: ${imageName}:${imageTag}|g' deployment.yaml

          echo "📦 Updated deployment.yaml:"
          grep image deployment.yaml

          # Apply manifests
          kubectl apply -f deployment.yaml -n ${namespace}

          # Wait for rollout
          kubectl rollout status deployment/jenkins-app \
            -n ${namespace} \
            --timeout=120s
        """
    }
}
