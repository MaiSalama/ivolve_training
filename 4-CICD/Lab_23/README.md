
# Lab 23 – CI/CD Pipeline with Jenkins Agents & Shared Libraries

## 🎯 Lab Objective

-   Use **Jenkins agents (slaves)** to execute pipelines
-   Use a **Jenkins Shared Library** to avoid duplicated pipeline code
-   Reuse the same library in **multiple pipelines**
-   Implement a full CI/CD flow:
    
    1.  RunUnitTest
        
    2.  BuildApp
        
    3.  BuildImage
        
    4.  ScanImage
        
    5.  PushImage
        
    6.  RemoveImageLocally
        
    7.  DeployOnK8s

## Step 1: Create Jenkins shared library

<img width="421" height="279" alt="image" src="https://github.com/user-attachments/assets/8ccda664-4119-4308-8d12-05c98a98db66" />

## Step 2: Configure Shared Library in Jenkins

<img width="1919" height="869" alt="image" src="https://github.com/user-attachments/assets/912f48c3-dca8-46e5-9944-8e91e504ebb1" />

## Step 3: Configure Jenkins Agent

<img width="1918" height="619" alt="image" src="https://github.com/user-attachments/assets/962f789c-3652-4061-8c4c-347266643d5a" />
<img width="1901" height="867" alt="image" src="https://github.com/user-attachments/assets/57a069ce-552d-4dbe-b4e0-28fe34ef2955" />

# Run the agent as a Docker container with:

    docker run -d \
      --name jenkins-agent \
      -v /var/run/docker.sock:/var/run/docker.sock \
      jenkins/inbound-agent

<img width="1028" height="472" alt="image" src="https://github.com/user-attachments/assets/93505455-e4f3-4a90-875e-60ca2f54e82e" />

## Step 4: Write Jenkinsfile using Shared Library & Agent

## Step 5: Create and Run Pipeline


