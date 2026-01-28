# 🧪 Lab 17: Pod Resource Management (CPU & Memory)
## STEP 1 -  Verify current QOS
    kubectl describe pod nodejs-app-9d9bbfc57-fwrgs -n ivolve | grep -i qos
<img width="1282" height="45" alt="image" src="https://github.com/user-attachments/assets/68cc240a-8560-4b47-9579-44b05192d20a" />

## STEP 2 -  Add resources section 
Inside the **Node.js container spec**, add:

    resources:  
    	requests:  	
    		cpu:  "1"  	
    		memory:  "1Gi"  
    	limits:  
    		cpu:  "2"  
    		memory:  "2Gi"


## STEP 3 -  Apply and Restart

    kubectl apply -f nodejs-deployment.yaml
    kubectl rollout restart deployment nodejs-app -n ivolve

<img width="1135" height="98" alt="image" src="https://github.com/user-attachments/assets/9a7774dd-b81b-4a35-891c-6ac68b35e07b" />

## STEP 4 — Verify pod was recreated

`kubectl get pods -n ivolve` 
<img width="893" height="97" alt="image" src="https://github.com/user-attachments/assets/21289526-7bcf-4fe2-bc05-4d0b4a683362" />


## STEP 5 — Verify requests & limits 

`kubectl describe pod <new-nodejs-pod> -n ivolve`

<img width="1175" height="783" alt="image" src="https://github.com/user-attachments/assets/4c8a6d71-a75f-4db3-b914-dd5f432036bb" />
<img width="593" height="143" alt="image" src="https://github.com/user-attachments/assets/6b35b825-24db-4b28-a8b8-4db25d23fbad" />

## STEP 6 — Monitor runtime usage 

`kubectl top pod -n ivolve`

<img width="925" height="104" alt="image" src="https://github.com/user-attachments/assets/5e95c6ba-b5c0-4e41-a5c3-a655507e2b88" />





