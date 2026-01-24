# 🧪  Lab 14 – MySQL StatefulSet with Headless Service
## 🔹 Prequisites
-   ✅ KinD cluster running
-   ✅ Namespace `ivolve` exists
-   ✅ You already created:
    -   `mysql-secret` (with `MYSQL_ROOT_PASSWORD`)
    -   PV + PVC bound (from Lab 13)
-   ✅ You have a taint on a worker node
<img width="1134" height="613" alt="image" src="https://github.com/user-attachments/assets/2df343ae-b696-48d2-b425-9368341f99fe" />

## 🔹 Step 1: Create a Headless Service (REQUIRED for StatefulSet)

📌 **Why first?**  
StatefulSets rely on a **headless service** for stable DNS.

### `mysql-headless-svc.yaml`

    apiVersion:  v1 
    kind:  Service  
    metadata:  
	    name:  mysql  
	    namespace:  ivolve  
    spec:  
	    clusterIP:  None  
	    selector:  
		    app:  mysql  
	    ports:  
		    -  port:  3306  
	         name:  mysql 

Apply:

    kubectl apply -f mysql-headless-svc.yaml 

Verify:

    kubectl get svc -n ivolve
    
<img width="1149" height="461" alt="image" src="https://github.com/user-attachments/assets/b8fcafa5-6d5d-405a-9db2-ffc0cbb05404" />


## 🔹 Step 2: Create the MySQL StatefulSet

### `mysql-statefulset.yaml`

<img width="488" height="600" alt="image" src="https://github.com/user-attachments/assets/5e3b81be-6ef4-4b3a-885d-29b128f148cb" />

Apply:

    kubectl apply -f mysql-statefulset.yaml

## 🔹 Step 3: Verify StatefulSet, Pod, and PVC

### StatefulSet
    kubectl get statefulset -n ivolve

### Pod
    kubectl get pods -n ivolve 

### PVC (created automatically)

    kubectl get pvc -n ivolve 

<img width="1141" height="266" alt="image" src="https://github.com/user-attachments/assets/a1a5f1c6-b3b2-4634-b576-b41523b8decb" />

## 🔹 Step 4: Confirm MySQL is operational

### Exec into the MySQL pod

    kubectl exec -it mysql-0 -n ivolve -- bash

Connect using root password from secret:

    mysql -u root -p

Enter the password you encoded earlier.

Inside MySQL:

    SHOW DATABASES;

<img width="1313" height="465" alt="image" src="https://github.com/user-attachments/assets/1c5ba1f5-3107-40d4-880e-a91d5c4980ae" />





