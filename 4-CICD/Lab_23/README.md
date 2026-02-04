
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

## Step 4: Build a custom Jenkins Agent that has Java and Maven

### 1️⃣ Create Dockerfile

    FROM jenkins/inbound-agent:latest
    
    USER root
    
    RUN apt-get update && \
        apt-get install -y maven docker.io curl && \
        rm -rf /var/lib/apt/lists/*
        
    # Add jenkins user to docker group
    RUN groupadd -f docker && usermod -aG docker jenkins
    
    USER jenkins

### 2️⃣ Build it

    docker build -t jenkins-agent-maven 

### 3️⃣ Run agent using this image

    docker run -d \
      --name docker-agent \
      --network jenkins_jenkins \
      --group-add 1001 \
      -e JENKINS_URL=http://jenkins:8080 \
      -e JENKINS_AGENT_NAME=docker-agent \
      -e JENKINS_SECRET=<SECRET_FROM_JENKINS> \
      -v /var/run/docker.sock:/var/run/docker.sock \
      jenkins-agent-maven

## Step 5: Write Jenkinsfile using Shared Library & Agent

## Step 6: Create and Run Pipeline

<img width="1918" height="868" alt="image" src="https://github.com/user-attachments/assets/283af112-fa8a-4c1d-999e-0a7bd61f9caa" />

## Step 7: Create and Run Another Pipeline Using The Same Shared Library & Agent

<img width="1919" height="868" alt="image" src="https://github.com/user-attachments/assets/9e58ef78-c4c0-41ac-96e8-e599fd6b0382" />





