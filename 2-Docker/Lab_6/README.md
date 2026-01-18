# Lab 6 – Managing Docker Environment Variables Across Build and Runtime

## Objective

The objective of this lab is to **understand and manage environment variables in Docker at different stages**, including:

-   Passing variables at **container runtime**
    
-   Using **environment variable files**
    
-   Defining **default environment variables in the Dockerfile**
    

This lab demonstrates how Docker applications can be configured **without changing the image itself**, which is a core DevOps and cloud principle.

## Step 1: Clone the Application Code

The application source code was cloned from GitHub:

    git clone https://github.com/Ibrahim-Adel15/Docker-3.git

The repository contains:

-   `app.py` → Flask application
    
-   The application reads environment variables:
    
    -   `APP_MODE`
        
    -   `APP_REGION`
    
  ## Step 2: Write the Dockerfile

A `Dockerfile` was created to containerize a Python Flask application.

### Dockerfile Explanation
```
#Uses a lightweight Python base image
FROM  python:3.11-slim

#Sets the working directory inside the container
WORKDIR  /app

#Copies the application source code into the container
COPY  .  .

#Installs Flask inside the container
RUN  pip  install  flask

#Sets default environment variables
#These values apply if no overrides are provided at runtime
ENV  APP_MODE=production
ENV  APP_REGION=canada-west

#Documents the application listening port
EXPOSE  8080

#Runs the Flask application when the container starts
CMD  ["python",  "app.py"]
```
## Step 3: Build the Docker Image

The Docker image was built using:

    docker build -t app-env .

## Step 4: Run Containers with Different Environment Variable Configurations

Three containers were run using **three different methods** of setting environment variables.

### 🔹 Case 1: Pass Environment Variables via Command Line

    docker run -d -p 8080:8080 \
      -e APP_MODE=development \
      -e APP_REGION=us-east \
      --name env-container-1 app-env

📌 Environment variables are passed **directly at runtime** using `-e`.

----------

### 🔹 Case 2: Pass Environment Variables Using an Env File

An environment file `staging.env` was created:

`APP_MODE=staging
APP_REGION=us-west` 

The container was started using:

    docker run -d -p 8081:8080 \
      --env-file staging.env \
      --name env-container-2 app-env

📌 This approach is cleaner and preferred for managing multiple variables.

----------

### 🔹 Case 3: Use Environment Variables Defined in the Dockerfile

    docker run -d -p 8082:8080 \
      --name env-container-3 app-env 

📌 Since no variables were passed at runtime, Docker used the **default values** defined in the Dockerfile:

-   `APP_MODE=production`
    
-   `APP_REGION=canada-west`
## Step 5: Test the Application

Each container was tested using:

    curl http://localhost:<PORT>

The output confirmed that the application correctly reads and displays:

-   Application mode
    
-   Application region
<img width="994" height="398" alt="image" src="https://github.com/user-attachments/assets/325a7df2-c0e4-43e3-8a19-7c557afdba0b" />
<img width="1043" height="373" alt="image" src="https://github.com/user-attachments/assets/fa432d91-4393-413d-89b7-ebd3e10e5e15" />
<img width="1049" height="364" alt="image" src="https://github.com/user-attachments/assets/aa0a60e5-5ac0-4b0d-9138-443aabc7902c" />

## Step 6: Stop and Remove Containers

After testing, the containers were stopped and removed:
```
docker stop env-container-1 env-container-2 env-container-3
docker rm env-container-1 env-container-2 env-container-3
```

## Key Learnings

-   Docker environment variables can be set at **multiple levels**
    
-   Runtime environment variables **override** Dockerfile defaults
    
-   Environment files are ideal for managing multiple variables
    
-   Images should remain immutable; configuration should be injected at runtime
    

----------

## Environment Variable Precedence (Important)


`docker run -e`      ->  Highest
`--env-file`            ->  Medium
`ENV` in Dockerfile   ->   Lowest

----------

## Final Summary

In this lab, we demonstrated how to manage Docker environment variables across build and runtime. We successfully ran the same Docker image in **development**, **staging**, and **production** configurations without rebuilding the image, following real-world DevOps best practices.

