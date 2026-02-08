# Lab 25 – GitOps Workflow with Jenkins & ArgoCD

## Objective

Implement a **GitOps-based CI/CD workflow** where:

-   Jenkins **builds and pushes Docker images**
    
-   Jenkins **updates Kubernetes manifests in Git**
    
-   **ArgoCD** automatically deploys changes to the Kubernetes cluster
    
-   Jenkins **does NOT deploy to Kubernetes directly**
    

----------

## Architecture Overview

    Developer → GitHub (Source  Code)
                   ↓
                Jenkins
       - Build App
       - Build Docker Image - Push Image - Update deployment.yaml - Commit & Push to GitHub
                   ↓
            GitHub (K8s Manifests)
                   ↓
                ArgoCD
            - Detect Git change
            - Sync to Kubernetes

----------

## Prerequisites

-   Docker installed
-   Kubernetes cluster running (Docker Desktop / Kind)
-   Jenkins running with:
    -   Docker access
    -   Git access
-   GitHub repository:
    -   [https://github.com/MaiSalama/ivolve_training](https://github.com/MaiSalama/ivolve_training)
-   DockerHub account
-   kubectl installed
    

----------

## Repository Structure

    4-CICD/
    └── Lab_25/
        ├── Jenkins_App/
        │   ├── Dockerfile
        │   ├── Jenkinsfile
        │   └── src/
        └── k8s/
            ├── deployment.yaml  

----------

## Step 1 – Install ArgoCD

Create ArgoCD namespace:

    kubectl create namespace argocd

Install ArgoCD:

    kubectl apply -n argocd \
      -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

Wait for pods to be ready:

    kubectl get pods -n argocd 

----------

## Step 2 – Access ArgoCD UI

Port forward ArgoCD server:

    kubectl port-forward svc/argocd-server -n argocd 8080:443 

Open browser:

    https://localhost:8080 

----------

### Get Admin Password

    kubectl get secret argocd-initial-admin-secret \
      -n argocd \
      -o jsonpath="{.data.password}" | base64 -d 

Login:
-   Username: `admin`
-   Password: output above
    

----------

## Step 3 – Kubernetes Manifests (GitOps Source)

### deployment.yaml

📄 `5-ArgoCD/Lab_25/k8s/deployment.yaml`

> ⚠️ Jenkins will **only modify the image tag**

----------

## Step 4 – Create ArgoCD Application

In ArgoCD UI → **New App**
Application Name → `jenkins-app`
Project→`default`
Sync Policy→**Automatic**
Repository URL→`https://github.com/MaiSalama/ivolve_training.git`
Revision→`main`
Path→`5-ArgoCD/Lab_25/k8s`
Cluster→`https://kubernetes.default.svc`
Namespace→`dev`

Click **Create**.

----------

## Step 5 – Jenkins Pipeline (GitOps Style)

### Jenkinsfile

📄 `5-ArgoCD/Lab_25/Jenkins_App/Jenkinsfile`

----------

## Step 6 – Run the Pipeline

<img width="1903" height="652" alt="image" src="https://github.com/user-attachments/assets/7781bfc9-53a3-4192-82b4-488812ad24e7" />


----------

## Step 7 – Verify ArgoCD Sync

In ArgoCD UI:

-   Application should auto-sync
-   Status: **Healthy / Synced**
    
<img width="1339" height="647" alt="image" src="https://github.com/user-attachments/assets/4d2f561c-337d-4609-a160-57ed9d3141d2" />

----------

## Step 8 – Verify Deployment

    kubectl get pods -n dev
    kubectl describe pod <pod-name> -n dev| grep Image 

<img width="1205" height="191" alt="image" src="https://github.com/user-attachments/assets/33b1d027-ddcd-49d0-b9b1-758737ac73da" />


Expected:

    imaisalama/jenkins-app:<BUILD_NUMBER>

----------

## Key GitOps Principles Applied

-   Jenkins **does not access Kubernetes**
-   Git is the **single source of truth**
-   ArgoCD **pulls desired state**
-   Deployments are **auditable & reproducible**
   

----------

## Lab Outcome

✅ Jenkins builds and pushes images  
✅ Git updated automatically  
✅ ArgoCD deploys without Jenkins kubectl  
✅ Full GitOps workflow implemented
