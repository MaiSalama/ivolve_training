# 🧪 Lab 20:  Securing Kubernetes with RBAC and Service Accounts

## STEP 1 — Create `jenkins-sa` ServiceAccount

    kubectl create serviceaccount jenkins-sa -n ivolve 
    kubectl get sa -n ivolve

<img width="1147" height="148" alt="image" src="https://github.com/user-attachments/assets/1d6a8dbd-9533-4f53-a60c-6bc99de68766" />



## STEP 2 — Create a Secret & retrieve the ServiceAccount token

Create token secret YAML

<img width="639" height="288" alt="image" src="https://github.com/user-attachments/assets/156aad14-66e8-43b5-80d9-35a0a9b601ef" />

Apply it:

    kubectl apply -f jenkins-sa-token.yaml

Retrieve the token:

    kubectl get secret jenkins-sa-token -n ivolve -o jsonpath='{.data.token}' | base64 --decode

<img width="1409" height="250" alt="image" src="https://github.com/user-attachments/assets/d36e2b6a-86ea-4806-b509-97685c238bb9" />


## STEP 3 — Create the `pod-reader` Role

This role allows **read-only access to Pods**.

<img width="546" height="301" alt="image" src="https://github.com/user-attachments/assets/b0599cbe-2560-4ac7-896d-9f1603cd7015" />

Apply:

    kubectl apply -f pod-reader-role.yaml

Verify:

    kubectl get role -n ivolve

<img width="995" height="195" alt="image" src="https://github.com/user-attachments/assets/03efb492-7ae7-49aa-ac59-498b39f4ff6d" />


# STEP 4 — Bind Role to `jenkins-sa` (RoleBinding)

Apply:

    kubectl apply -f pod-reader-binding.yaml 

Verify:

    kubectl get rolebinding -n ivolve

<img width="1030" height="628" alt="image" src="https://github.com/user-attachments/assets/3f2f2235-a09d-4a2b-962e-dbee796a2ff6" />

# STEP 5 — Validate permissions 

### ✅ Test allowed actions (should work)

    kubectl auth can-i list pods \
      --as=system:serviceaccount:ivolve:jenkins-sa \
      -n ivolve 

    kubectl auth can-i get pods \
      --as=system:serviceaccount:ivolve:jenkins-sa \
      -n ivolve 

<img width="971" height="185" alt="image" src="https://github.com/user-attachments/assets/d120da49-1e91-4eeb-b098-b9422a45e7f8" />

### ❌ Test forbidden actions (should FAIL)

    kubectl auth can-i delete pods \
      --as=system:serviceaccount:ivolve:jenkins-sa \
      -n ivolve

    kubectl auth can-i list services \
      --as=system:serviceaccount:ivolve:jenkins-sa \
      -n ivolve 

<img width="1019" height="182" alt="image" src="https://github.com/user-attachments/assets/096117b0-e7b3-4061-b8c9-db918a2e9931" />


