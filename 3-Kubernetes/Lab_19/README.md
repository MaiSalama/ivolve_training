# 🧪 Lab 19: Node-Wide Pod Management with DaemonSet

## STEP 1 — Create the `monitoring` namespace
    kubectl create namespace monitoring 
Verify:
    
    kubectl get ns
    
<img width="953" height="251" alt="image" src="https://github.com/user-attachments/assets/7c678a03-9366-417d-a0c7-228959c19d2a" />

## STEP 2 — Create DaemonSet YAML for node-exporter
<img width="547" height="720" alt="image" src="https://github.com/user-attachments/assets/9963cec0-9618-45c0-a988-1363ad69cb8d" />


## STEP 3 — Apply the DaemonSet

kubectl apply -f node-exporter-daemonset.yaml

Verify:

kubectl get daemonset -n monitoring

<img width="1056" height="149" alt="image" src="https://github.com/user-attachments/assets/9123a975-5026-4d7b-bd7d-537227d63497" />


## STEP 4 — Validate 1 pod per node

    kubectl get pods -n monitoring -o wide

Expected:
-   One `node-exporter-xxxxx` **per node**
-   Each pod scheduled on a **different node**

  <img width="1223" height="204" alt="image" src="https://github.com/user-attachments/assets/29337efe-1b0b-400c-bec4-44c5065fd2de" />

  ## STEP 5 — Confirm metrics exposure (:9100/metrics)

Pick **any node-exporter pod**:

`kubectl port-forward pod/<node-exporter-pod> -n monitoring 9100:9100` 

Then open in browser or curl:

`curl http://localhost:9100/metrics` 

  <img width="1285" height="129" alt="image" src="https://github.com/user-attachments/assets/ff2068bb-d8d2-410f-aca7-716be80650a3" />

  <img width="979" height="330" alt="image" src="https://github.com/user-attachments/assets/f6ded920-5715-4a54-a142-1a4dfa5eb49a" />

  

