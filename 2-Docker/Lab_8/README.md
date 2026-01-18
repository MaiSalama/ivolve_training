# Lab 8 – Custom Docker Network for Microservices Communication

## Objective

The objective of this lab is to **understand Docker networking in a microservices setup** by:

-   Creating a **custom Docker bridge network**
    
-   Running frontend and backend services on the same network
    
-   Verifying **container-to-container communication using service names**
    
-   Demonstrating why containers on different networks **cannot communicate**
## Step 1: Clone the Application Code

The frontend and backend applications were cloned from GitHub:

    git clone https://github.com/Ibrahim-Adel15/Docker5.git

The repository contains:
-   `frontend/` → Python frontend service
-   `backend/` → Python backend service
-   Each service runs on port `5000`

## Step 2: Write Dockerfile for the Frontend Service

A `Dockerfile` was created inside the `frontend` directory.

### Frontend Dockerfile

    FROM python:3.11-slim
    
    WORKDIR /app
    
    COPY requirements.txt .
    RUN pip install -r requirements.txt
    
    COPY . .
    
    EXPOSE 5000
    
    CMD ["python", "app.py"]

### Build the frontend image

    docker build -t frontend-app ./frontend

----------

## Step 3: Write Dockerfile for the Backend Service

A `Dockerfile` was created inside the `backend` directory.

### Backend Dockerfile

    FROM python:3.11-slim
    
    WORKDIR /app
    
    COPY . .
    
    RUN pip install flask
    
    EXPOSE 5000
    
    CMD ["python", "app.py"]

### Build the backend image

    docker build -t backend-app ./backend
## Step 4: Create a Custom Docker Network

A custom Docker bridge network was created:

`docker network create ivolve-network` 

Verification:

`docker network ls` 

📌 Custom networks provide:
-   Automatic DNS resolution
-   Container name-based communication
-   Better isolation than the default bridge

## Step 5: Run Backend Container on Custom Network

    docker run -d \
      --name backend \
      --network ivolve-network \
      backend-app

📌 The backend container is now reachable by its **container name** (`backend`) inside the network.

----------

## Step 6: Run Frontend Container on Custom Network

    docker run -d \
      -p 5000:5000 \
      --name frontend1 \
      --network ivolve-network \
      frontend-app 

📌 `frontend1` can communicate with `backend` using:

    http://backend:5000
    
## Step 7: Run Another Frontend on Default Network

    docker run -d \
      -p 5001:5000 \
      --name frontend2 \
      frontend-app 

📌 This container runs on the **default bridge network**, not `ivolve-network`.

## Step 8: Verify Communication Between Containers

### ✅ Successful Communication
-   `frontend1` → `backend`
-   Both containers are on `ivolve-network`
-   Docker DNS resolves container names automatically

### ❌ Failed Communication
-   `frontend2` → `backend`
-   Containers are on **different networks**
-   Docker DNS resolution does not work across networks
> Note: curl is not found in this image by default 
> apt update && apt install -y curl

<img width="991" height="132" alt="image" src="https://github.com/user-attachments/assets/514fd854-c6f0-4aa8-937b-8ce0ab31c89c" />

<img width="489" height="270" alt="image" src="https://github.com/user-attachments/assets/cc527fd8-5535-4328-ae43-3cf90966b4a7" />

This confirms:

> Containers must be on the **same Docker network** to communicate using service names.

----------

## Step 9: Clean Up

### Stop and remove containers

    docker stop frontend1 frontend2 backend
    docker rm frontend1 frontend2 backend

### Delete the custom network

    docker network rm ivolve-network

 

----------

## Key Learnings

-   Docker networks enable container-to-container communication
-   Custom bridge networks provide built-in DNS
-   Containers on the same network can communicate using **container names**
-   Containers on different networks are isolated by default


