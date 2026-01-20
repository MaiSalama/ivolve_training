# Lab 10 – Node Isolation Using Taints (KinD on Windows)

## 🎯 Lab Objective

-   Create a Kubernetes cluster with **2 nodes**
-   **Taint one node** so that:
    -   No pods can be scheduled on it
-   Verify the taint using `kubectl describe nodes`
## 🔹 Step 0: Verify tools are installed
Run:

    kubectl version --client
    kind version
    docker --version 
<img width="879" height="173" alt="image" src="https://github.com/user-attachments/assets/87063166-59a0-42ab-af30-b12c8d1036f6" />

## 🔹 Step 1: Create a KinD cluster with **2 worker nodes**

Create a file called `kind-2-nodes.yaml`:

    kind: Cluster  
    apiVersion: kind.x-k8s.io/v1alpha4  
    nodes:  
    	-  role:  control-plane  
    	-  role:  worker  
    	-  role:  worker 

Create the cluster:

    kind create cluster --name taint-lab --config kind-2-nodes.yaml

✅ This creates:
-   1 control-plane   
-   2 worker nodes
<img width="1320" height="636" alt="image" src="https://github.com/user-attachments/assets/28e65ed6-c369-47de-b7fc-da24c96d408a" />

----------

## 🔹 Step 2: Verify nodes

    kubectl get nodes
<img width="821" height="107" alt="image" src="https://github.com/user-attachments/assets/9db5b1a4-5012-4444-9d30-a2e046287472" />

## 🔹 Step 3: Taint one worker node

Pick **one worker node** (example: `taint-lab-worker`).
Run:

    kubectl taint nodes taint-lab-worker node=worker:NoSchedule 
    
## 🔹 Step 4: Describe node to verify the taint 
    kubectl describe node taint-lab-worker
    
<img width="1103" height="306" alt="image" src="https://github.com/user-attachments/assets/c5f6964a-6495-416f-80bd-9a9a3a3c9638" />

## 🔹 Step 5: Describe all nodes (comparison)

    kubectl describe nodes | grep -A 3 Taints
    
<img width="985" height="261" alt="image" src="https://github.com/user-attachments/assets/d1d3cb0b-cde5-45e8-b150-d842092ec1a8" />

### 🧠 What this taint means 
**No pod will be scheduled on this node unless it has a toleration.**
### 🧠 Why does the control-plane (master) node have a taint?
Because **Kubernetes adds it by default** to **protect the control plane**.
## 🔹 Step 6: Prove scheduling behavior

Create a test pod:

    kubectl run test-pod --image=nginx 

Check where it landed:

    kubectl get pod -o wide 
    
<img width="944" height="107" alt="image" src="https://github.com/user-attachments/assets/11c5507b-4215-40e9-8385-d8460fe7fea5" />

You will see the pod scheduled on:
-   ❌ NOT the tainted node
-   ✅ The other worker node
----------

## 🔹 Step 7: Remove the taint

    kubectl taint nodes taint-lab-worker node=worker:NoSchedule- 

Verify:

    kubectl describe node taint-lab-worker
    
<img width="1120" height="280" alt="image" src="https://github.com/user-attachments/assets/78c30c70-976c-46dd-9fb2-04d8cca82ae4" />

----------

##  Step 8: Clean up cluster (when done)

    kind delete cluster --name taint-lab

<img width="946" height="72" alt="image" src="https://github.com/user-attachments/assets/c0a6ba91-79c4-497e-9de2-8dc1946f362a" />

