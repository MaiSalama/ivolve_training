# 🧪 Lab 18: Control Pod-to-Pod Traffic via Network Policy
## Step 1 — Create the NetworkPolicy YAML
<img width="781" height="605" alt="image" src="https://github.com/user-attachments/assets/a623edee-4fa9-40c3-9741-339888577405" />

## Step 2 — Apply the Policy


    kubectl apply -f allow-app-to-mysql.yaml
    kubectl get networkpolicy -n ivolve

<img width="1068" height="122" alt="image" src="https://github.com/user-attachments/assets/0ad09d47-8164-4359-86da-a97022618cb9" />


## 🧪 Step 3 — Verify the Policy Works 

From Node.js pod → MySQL 

`which nc` 
`nc -zv mysql 3306` 
`mysql 3306 open` 

✅ **Allowed by NetworkPolicy**

<img width="1184" height="143" alt="image" src="https://github.com/user-attachments/assets/f896d6ae-c62c-4f53-9670-6b51ce1e636f" />

