# Lab 11 – Namespace Management & ResourceQuota

## 🎯 Lab Goal (in simple words)

-   Create a **namespace** called `ivolve`
-   Limit the namespace to **maximum 2 pods**
-   Prove that:
    -   2 pods can run
    -   The 3rd pod is **rejected**
----------
## 🔹 Prequisites
-   ✅ KinD cluster is running
-   ✅ `kubectl` works (`kubectl get nodes` succeeds)
  
  <img width="804" height="116" alt="image" src="https://github.com/user-attachments/assets/f753ebf6-99d1-47a1-9f35-c5e168081109" />

----------
## 🔹 Step 1: Create the namespace

    kubectl create namespace ivolve 

Verify:

    kubectl get namespaces
<img width="907" height="198" alt="image" src="https://github.com/user-attachments/assets/f4cd2f30-d7a8-4e04-afa1-45ed2dc5218d" />

----------

## 🔹 Step 2: Create a ResourceQuota YAML

Create a file called `pod-quota.yaml`:
   

    apiVersion:  v1 
    kind:  ResourceQuota  
    metadata:  
        name:  ivolve-pod-quota  
        namespace:  ivolve  
    spec: 
      hard:  
        pods:  "2" 

📌 This quota limits the **total number of pods** in the namespace.

----------

## 🔹 Step 3: Apply the ResourceQuota

    kubectl apply -f pod-quota.yaml 
Verify:

    kubectl get resourcequota -n ivolve 

Describe it:

    kubectl describe resourcequota ivolve-pod-quota -n ivolve
    
<img width="1141" height="516" alt="image" src="https://github.com/user-attachments/assets/111e4f4f-d747-41c1-9189-626c681da1f8" />

----------
## 🔹 Step 4: Create test pods (to prove quota works)

### Create first pod

    kubectl run pod1 --image=nginx -n ivolve

### Create second pod

    kubectl run pod2 --image=nginx -n ivolve 

Check:

kubectl get pods -n ivolve

You should see **2 running pods**.

----------

## 🔹 Step 5: Try to create a 3rd pod (THIS SHOULD FAIL)

    kubectl run pod3 --image=nginx -n ivolve
<img width="994" height="329" alt="image" src="https://github.com/user-attachments/assets/8acb8800-dc3c-448b-8d55-8c90f744b670" />

✅ This proves the resource quota is enforced.

----------

## 🔹 Step 6: Verify quota usage

    kubectl describe resourcequota ivolve-pod-quota -n ivolve
<img width="1101" height="122" alt="image" src="https://github.com/user-attachments/assets/0d4b3a6a-0f0b-41ee-b82a-884a3de15b0c" />
