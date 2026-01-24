# Lab 12 – ConfigMaps & Secrets (Config + Sensitive Data)

## 🎯 Lab Goal (simple explanation)

-   Store **non-sensitive config** in a **ConfigMap**
-   Store **passwords** in a **Secret**
-   Use **base64 encoding** for Secret values
-   Follow **best practices** (separation of concerns)
## 🔹 Prequisites
-   ✅ KinD cluster is running
-   ✅  `kubectl`  works (`kubectl get nodes`  succeeds)
-   ✅ Namespace `ivolve` exists.
<img width="888" height="190" alt="image" src="https://github.com/user-attachments/assets/458fb959-e402-46c0-a0f7-30f3b79817c2" />

## 🔹 Step 1: Create the ConfigMap (non-sensitive data)

### 📌 What goes in the ConfigMap
- DB_HOST : MySQL service hostname
- DB_USER: Application DB user


### Create `mysql-configmap.yaml`

    apiVersion:  v1  
    kind:  ConfigMap  
    metadata:  
    	name:  mysql-config  
    	namespace:  ivolve  
    data:  
    	DB_HOST:  mysql  
    	DB_USER:  ivolve_user 

Apply it:

    kubectl apply -f mysql-configmap.yaml 

Verify:

    kubectl get configmap -n ivolve
    kubectl describe configmap mysql-config -n ivolve 

<img width="1140" height="677" alt="image" src="https://github.com/user-attachments/assets/50ea3121-a223-4c65-aba4-d75bea894aaf" />

----------

## 🔹 Step 2: Prepare base64 values for Secrets

Secrets **must be base64 encoded**.

### Encode values (PowerShell-safe)

    [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes("dbpass"))
    [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes("rootpass123"))
----------

## 🔹 Step 3: Create the Secret (sensitive data)

### 📌 What goes in the Secret

- DB_PASSWORD: App database password
- MYSQL_ROOT_PASSWORD: MySQL root password
  
### Create `mysql-secret.yaml`

    apiVersion:  v1  
    kind:  Secret  
    metadata:  
    	name:  mysql-secret  
    	namespace:  ivolve  
    type:  Opaque  
    data:  
    	DB_PASSWORD: ZGJwYXNz
    	MYSQL_ROOT_PASSWORD: cm9vdHBhc3MxMjM=

Apply it:

    kubectl apply -f mysql-secret.yaml 

Verify:

    kubectl get secrets -n ivolve
    kubectl describe secret mysql-secret -n ivolve

  <img width="989" height="277" alt="image" src="https://github.com/user-attachments/assets/aed03613-52e8-4b04-ab35-db02b381e838" />



⚠️ Note: Values remain encoded when viewed.

----------

