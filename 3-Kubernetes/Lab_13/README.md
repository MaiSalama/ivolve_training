# Lab 13 – Persistent Storage Setup for Application Logging

## 🎯 Lab Goal (simple words)

-   Create a **PersistentVolume (PV)**:
    -   1Gi size
    -   `hostPath` storage
    -   `ReadWriteMany`
    -   `Retain` reclaim policy
-   Create a **PersistentVolumeClaim (PVC)**
    -   Requests 1Gi
    -   Uses `ReadWriteMany`
-   Ensure **PV and PVC bind successfully**

## 🔹 Prequisites
-   ✅ KinD cluster is running
-   ✅  `kubectl`  works (`kubectl get nodes`  succeeds)
-   ✅ Namespace `ivolve` exists.
<img width="888" height="190" alt="image" src="https://github.com/user-attachments/assets/458fb959-e402-46c0-a0f7-30f3b79817c2" />


## ⚠️ Important KinD Note (READ FIRST)
KinD runs Kubernetes **inside Docker containers**, so:
-   `hostPath` refers to the **node container filesystem**
-   We must create `/mnt/app-logs` **inside the node**    
-   Permissions must be `777`
## 🔹 Step 1: Identify a worker node (app node)
List nodes:

    kubectl get nodes

We’ll use **taint-lab-worker** as the app node  

<img width="811" height="85" alt="image" src="https://github.com/user-attachments/assets/6afbefb9-ef74-4a1b-8313-eaae89c0316c" />

## 🔹 Step 2: Create `/mnt/app-logs` on the node (KinD-specific)

### Exec into the node container (IMPORTANT)

    docker ps 

Find the container name: taint-lab-worker  

Now exec into it:

    docker exec -it taint-lab-worker /bin/bash 

Inside the node:

    mkdir -p /mnt/app-logs 
    chmod 777 /mnt/app-logs 
    ls -ld /mnt/app-logs 

Exit node:

    exit 

<img width="1127" height="327" alt="image" src="https://github.com/user-attachments/assets/3d3a2e7e-e60f-40eb-aa86-c706829474b2" />

✅ The hostPath directory now exists on the node.

## 🔹 Step 3: Define the PersistentVolume (PV)

Create `pv-app-logs.yaml`:

    apiVersion:  v1 
    kind:  PersistentVolume 
    metadata:  
    	name:  app-logs-pv  
    spec:  
    	capacity:  
    		storage:  1Gi  
    	accessModes:  
    		-  ReadWriteMany  
    	persistentVolumeReclaimPolicy:  Retain  
    	storageClassName: ""
    	hostPath:  
    		path:  /mnt/app-logs 

Apply it:

    kubectl apply -f pv-app-logs.yaml 

Verify:

    kubectl get pv 

<img width="1152" height="445" alt="image" src="https://github.com/user-attachments/assets/a4dd6a4c-e9eb-456e-b976-3430d8bdcf75" />

----------

## 🔹 Step 4: Define the PersistentVolumeClaim (PVC)

Create `pvc-app-logs.yaml`:

    apiVersion:  v1 
    kind:  PersistentVolumeClaim  
    metadata:  
    	name:  app-logs-pvc  
    	namespace:  ivolve  
    spec:  
    	accessModes:  
    		-  ReadWriteMany
    	storageClassName: ""  
    	resources:  
    		requests:  
    			storage:  1Gi 

Apply it:

    kubectl apply -f pvc-app-logs.yaml 

----------

## 🔹 Step 5: Verify PVC ↔ PV binding (CRITICAL STEP)

    kubectl get pvc -n ivolve 

Check PV status:

    kubectl get pv 

<img width="935" height="108" alt="image" src="https://github.com/user-attachments/assets/44e1b524-0ce3-474f-91eb-8b8f95578f04" />

----------

## 🔹 Step 6: Describe to confirm access modes match

    kubectl describe pv app-logs-pv
    kubectl describe pvc app-logs-pvc -n ivolve

<img width="917" height="327" alt="image" src="https://github.com/user-attachments/assets/8bca7483-bfc5-4a71-9d84-68a8c1fa8940" />
<img width="993" height="253" alt="image" src="https://github.com/user-attachments/assets/55afe5e0-a853-4a30-b840-87dcf30e9be8" />

Verify:
-   AccessModes: `ReadWriteMany`
-   Capacity: `1Gi`
-   ReclaimPolicy: `Retain`
