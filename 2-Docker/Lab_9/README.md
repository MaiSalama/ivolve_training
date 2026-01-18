# Lab 9 – Containerized Node.js and MySQL Stack Using Docker Compose

## Objective

The objective of this lab is to **deploy a multi-container application using Docker Compose**, consisting of:

-   A **Node.js application**
-   A **MySQL database**
-   Persistent storage using **Docker volumes**
-   Service-to-service communication using **Docker networking**
    
This lab demonstrates how Docker Compose simplifies managing multi-service applications.

----------

## Step 1: Clone the Application Source Code

    git clone https://github.com/Ibrahim-Adel15/kubernets-app.git cd kubernets-app 

The repository contains:

-   `Dockerfile` → Node.js application image
-   Application source code
-   Health and readiness endpoints
-   Logs directory (`/app/logs`)
## Step 2: Application Requirements

The Node.js application:
-   Requires a **MySQL database**
-   Expects a database named **`ivolve`**
-   Reads database connection details from environment variables:
    -   `DB_HOST`
    -   `DB_USER`
    -   `DB_PASSWORD`

## Step 3: Create `docker-compose.yml`

A `docker-compose.yml` file was created to define the application stack.

### docker-compose.yml

    version:  "3.9" 
    
    services:  
    	app:  
    		build:  .  
    		ports:  
    			- "3000:3000"  
    		environment:  
    			DB_HOST:  db  
    			DB_USER:  root  
    			DB_PASSWORD:  rootpass  
    		depends_on:  
    			- db  
    	db:  
    		image:  
    			mysql:8.0  
    		environment:  
    			MYSQL_ROOT_PASSWORD:  rootpass  
    			MYSQL_DATABASE:  ivolve  
    		volumes:  
    			- db_data:/var/lib/mysql
      
	    volumes:  
	    	db_data:

## Step 4: Explanation of the Compose File

### App Service
-   Built from the local `Dockerfile`
-   Exposes port **3000**
-   Connects to the database using environment variables
-   Uses the service name `db` as `DB_HOST`
    
### Database Service
-   Uses the official **MySQL 8** image
-   Initializes a database named `ivolve`
-   Stores data in a Docker volume for persistence
    
### Volume
-   `db_data` persists MySQL data beyond container lifecycle
## Step 5: Run the Application Stack

    docker compose up -d

Verify services are running:

    docker compose ps

<img width="1126" height="140" alt="image" src="https://github.com/user-attachments/assets/709f1b3a-0c6e-4ca3-969e-b27a4668b764" />

----------

## Step 6: Verify Application Functionality

### 1️⃣ Verify Main Application

    curl http://localhost:3000 

<img width="1079" height="494" alt="image" src="https://github.com/user-attachments/assets/4e1d0ec7-bbdc-4c01-90e0-885518243def" />

----------

### 2️⃣ Verify Health Endpoint

    curl http://localhost:3000/health

<img width="1018" height="350" alt="image" src="https://github.com/user-attachments/assets/44bc6918-c7f5-4f04-afee-32fe8dcc94e3" />

----------

### 3️⃣ Verify Readiness Endpoint

    curl http://localhost:3000/ready 

<img width="1012" height="350" alt="image" src="https://github.com/user-attachments/assets/49475d50-4298-4cf2-bbe7-163d5521caa1" />

----------

### 4️⃣ Verify Application Logs

Access logs inside the app container:

    docker compose exec app ls /app/logs
    docker compose exec app cat /app/logs/access.log 

<img width="1132" height="253" alt="image" src="https://github.com/user-attachments/assets/f495aae8-e8eb-4a47-85cc-13da6e113355" />

This confirms logs are written correctly inside the container.

----------

## Step 7: Verify Database Persistence

Stop containers:

    docker compose down 

Start again:

    docker compose up -d

The application should still work, proving:

-   Database data is persisted using `db_data` volume
    
<img width="1126" height="274" alt="image" src="https://github.com/user-attachments/assets/d9c2e043-e953-49e0-a2c4-6c5a16d1e81f" />

----------

## Step 8: Push Docker Image to Docker Hub

### 1️⃣ Tag the image
`docker tag kubernets-app-main-app imaisalama/ivolve-app:latest` 

### 2️⃣ Login to Docker Hub
`docker login` 

### 3️⃣ Push the image
`docker push imaisalama/ivolve-app:latest` 

<img width="1119" height="435" alt="image" src="https://github.com/user-attachments/assets/f7e8356d-4fdb-4d1e-a6b6-86d1d170a827" />

----------

