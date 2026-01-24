# 🧪 Lab 15 – Node.js Deployment with ClusterIP Service


## 🔹 Step 1: Verify prerequisites based on previous labs

-   ✅ Namespace: `ivolve`
-   ✅ Static **PV already created** (from Lab 13)
-   ✅ A matching **PVC already exists** (RWX / 1Gi)
-   ✅ `mysql-config` ConfigMap exists
-   ✅ `mysql-secret` Secret exists
-   ✅ Worker node is tainted: `node=worker:NoSchedule`
-   ✅ You have a **Node.js image pushed to Docker Hub**

    kubectl get pv
    kubectl get pvc -n ivolve
    kubectl get configmap -n ivolve
    kubectl get secret -n ivolve

<img width="1030" height="467" alt="image" src="https://github.com/user-attachments/assets/5ef56e8f-be0f-4ceb-86f6-fa07c96245be" />

----------

## 🔹 Step 2: Create the Deployment 
<img width="324" height="629" alt="image" src="https://github.com/user-attachments/assets/dba80ab4-b4e0-4537-812a-57f3c243b3bf" />

----------

## 🔹 Step 3: Apply and Verify Deployment 

    kubectl apply -f nodejs-deployment.yaml`
    kubectl get deployments -n ivolve
    kubectl get pods -n ivolve
<img width="1025" height="285" alt="image" src="https://github.com/user-attachments/assets/81da422e-c963-45cd-9b2b-b41e8e2eb7e5" />

----------

## 🔹 Step 4: Create the ClusterIP Service

📌 ClusterIP provides internal load balancing across all READY pods.

<img width="298" height="203" alt="image" src="https://github.com/user-attachments/assets/c19e2084-c66b-41e3-acba-5f5b244109a2" />


----------

## 🔹 Step 5: Apply and Verify Service

    kubectl apply -f nodejs-service.yaml
    kubectl get svc -n ivolve

<img width="929" height="126" alt="image" src="https://github.com/user-attachments/assets/c355e162-486b-4078-bda6-bcc50cdece32" />



  

