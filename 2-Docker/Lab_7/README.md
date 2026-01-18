# Lab 7 – Docker Volumes and Bind Mounts with Nginx

## Objective

The objective of this lab is to **understand the difference between Docker volumes and bind mounts** and how they are used to:

-   Persist container data (logs)
    
-   Serve application content directly from the host
    
-   Verify data persistence beyond the container lifecycle

## Step 1: Create a Docker Volume for Nginx Logs

A Docker volume was created to persist Nginx logs:

    docker volume create nginx_logs

### Verify the volume exists

    docker volume ls

### Inspect the volume location

    docker volume inspect nginx_logs


📌 The volume is stored in Docker’s default volumes directory on the host  "/var/lib/docker/volumes"


<img width="1366" height="731" alt="image" src="https://github.com/user-attachments/assets/538ebdcc-4e49-494d-aba4-48c3c85b21d2" />


----------

## Step 2: Create a Bind Mount Directory on the Host

A directory was created on the host machine to serve static HTML content:

    mkdir -p nginx-bind/html 

Inside this directory, an `index.html` file was created:

    nano nginx-bind/html/index.html 
    
> Note: Use "notepad" instead of "nano" in windows powrshell.

Content:

    Hello from Bind Mount 

📌 This file will be served directly by Nginx from the host filesystem.

## Step 3: Run Nginx Container with Volume and Bind Mount

The Nginx container was started with:

-   A **Docker volume** for logs
    
-   A **bind mount** for HTML content
    
```
docker run -d \
  -p 8080:80 \
  --name nginx-container \
  -v nginx_logs:/var/log/nginx \
  -v $(pwd)/nginx-bind/html:/usr/share/nginx/html \
  nginx
  ```

> Note: use ${PWD} insetead of (pwd) in windows powershell.

### Explanation

-   `nginx_logs:/var/log/nginx` → persists Nginx logs
    
-   `$(pwd)/nginx-bind/html:/usr/share/nginx/html` → serves HTML from host
    
-   `-p 8080:80` → exposes Nginx on port 8080

## Step 4: Verify Nginx Page

The Nginx page was verified using:

    curl http://localhost:8080 

Expected output:

    Hello from Bind Mount

<img width="871" height="365" alt="image" src="https://github.com/user-attachments/assets/43762890-72e1-4c88-92c2-b761406a09bd" />


## Step 5: Modify HTML File on Host and Verify Again

The `index.html` file was modified on the host:

    nano nginx-bind/html/index.html 

New content:

    Hello from Bind Mount – Updated 

Without restarting the container, the page was tested again:

    curl http://localhost:8080 

📌 The updated content was served immediately, proving that **bind mounts reflect live changes**.

<img width="907" height="388" alt="image" src="https://github.com/user-attachments/assets/c907497b-2f88-4564-85bb-c7786c579d77" />


## Step 6: Verify Logs Are Stored in the Docker Volume

The logs were verified by inspecting the volume:

```
sudo  ls /var/lib/docker/volumes/nginx_logs/_data
access.log  error.log
```

and by accessing the logs inside the container
<img width="875" height="364" alt="image" src="https://github.com/user-attachments/assets/7a361255-8adf-452f-bea0-12daee664c28" />


📌 This confirms:

-   Logs are written inside the container
    
-   Logs persist independently of the container

## Step 7: Stop Container and Delete the Volume

### Stop and remove the container

```
docker stop nginx-container
docker rm nginx-container 
```

### Delete the volume

    docker volume rm nginx_logs


