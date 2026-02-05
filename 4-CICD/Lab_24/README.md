# 🧪 Lab 24 – Multibranch CI/CD Workflow (End-to-End)

## 🎯 Lab Goal 

> **One Jenkins job**  
> **Multiple Git branches (dev / stag / prod)**  
> → Jenkins builds & pushes image  
> → Deploys automatically to the **matching Kubernetes namespace**


## STEP 1 – Update Shared Library (deployOnK8s)

### `vars/deployOnK8s.groovy`

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

## Step 2: Multibranch-aware Jenkinsfile

    @Library('ci-lib') _
    
    pipeline {
      agent { label 'docker' }
    
      environment {
        APP_DIR    = '4-CICD/Lab_24/Jenkins_App-main'
        IMAGE_NAME = 'imaisalama/jenkins-app'
        IMAGE_TAG  = "${BUILD_NUMBER}"
      }
    
      stages {
    
        stage('Detect Environment') {
          steps {
            script {
              if (env.BRANCH_NAME == 'dev') {
                env.K8S_NAMESPACE = 'dev'
              } else if (env.BRANCH_NAME == 'stag') {
                env.K8S_NAMESPACE = 'stag'
              } else if (env.BRANCH_NAME == 'prod') {
                env.K8S_NAMESPACE = 'prod'
              } else {
                error "Unsupported branch: ${env.BRANCH_NAME}"
              }
    
              echo "Branch: ${env.BRANCH_NAME}"
              echo "Namespace: ${env.K8S_NAMESPACE}"
            }
          }
        }
    
        stage('RunUnitTest') {
          steps {
            runUnitTest(APP_DIR)
          }
        }
    
        stage('BuildApp') {
          steps {
            buildApp(APP_DIR)
          }
        }
    
        stage('BuildImage') {
          steps {
            buildImage(APP_DIR, IMAGE_NAME, IMAGE_TAG)
          }
        }
    
        stage('ScanImage') {
          steps {
            scanImage(IMAGE_NAME, IMAGE_TAG)
          }
        }
    
        stage('PushImage') {
          steps {
            pushImage(IMAGE_NAME, IMAGE_TAG)
          }
        }
    
        stage('RemoveImageLocally') {
          steps {
            removeImageLocally(IMAGE_NAME, IMAGE_TAG)
          }
        }
    
        stage('DeployOnK8s') {
          steps {
            deployOnK8s(APP_DIR, IMAGE_NAME, IMAGE_TAG, env.K8S_NAMESPACE)
          }
        }
      }
    
      post {
        success {
          echo "Deployment to ${env.K8S_NAMESPACE} successful 🎉"
        }
        failure {
          echo 'Pipeline failed'
        }
      }
    }

## Step 3 – Create **Multibranch Pipeline Job**

### Jenkins UI:

1.  **New Item**
2.  Name: `Lab24-Multibranch`
3.  Type: **Multibranch Pipeline**
4.  OK
    
### Branch Source:
-   GitHub
-   Repo URL: `https://github.com/MaiSalama/ivolve_training.git` 

### Build Configuration:
-   Script Path:
`4-CICD/Lab_24/Jenkins_App/Jenkinsfile`

## Step 4: Create Branches

    git checkout -b dev
    git push origin dev
    
    git checkout main
    git checkout -b stag
    git push origin stag
    
    git checkout main
    git checkout -b prod
    git push origin prod

<img width="543" height="612" alt="image" src="https://github.com/user-attachments/assets/78eafe60-fbde-4db0-b4cd-61c5a1b6f6ff" />

## Step 5 – Create Kubernetes namespaces

Run once (from your host):

    kubectl create namespace dev
    kubectl create namespace stag
    kubectl create namespace prod 

Verify:

    kubectl get ns

<img width="581" height="292" alt="image" src="https://github.com/user-attachments/assets/3228d0af-f6a2-4df3-a3f5-234c8180c584" />

## Step 6 - Scan repo for jenkins files and run pipelines

<img width="1152" height="847" alt="image" src="https://github.com/user-attachments/assets/07156e3b-311e-426a-b35e-2b8074420d2d" />
<img width="1581" height="412" alt="image" src="https://github.com/user-attachments/assets/40c59bb3-ab62-4da8-85c0-2e1c56336366" />
<img width="708" height="258" alt="image" src="https://github.com/user-attachments/assets/df4c02a4-edfd-4557-b3a8-292749393268" />






