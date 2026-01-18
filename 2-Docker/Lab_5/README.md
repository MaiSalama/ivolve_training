# Lab 5 – Multi-Stage Docker Build for a Java Application

## Objective

The objective of this lab is to **build and run a Java application using a multi-stage Docker build**.  
Multi-stage builds allow separating the **build environment** from the **runtime environment**, resulting in **smaller, more secure Docker images**.

## Step 1: Clone the Application Code

    `git clone https://github.com/Ibrahim-Adel15/Docker-1.git` 

## Step 2: Write a Multi-Stage Dockerfile

A multi-stage `Dockerfile` was created with **two stages**:

1.  **Build stage** (Maven + JDK)
    
2.  **Runtime stage** (Java runtime only)

   
```
#--------------------------------------------#
#------ Stage 1 : Run the Application -------#
#--------------------------------------------#
        
  #Uses a lightweight Java 17 runtime image
  #Does NOT include Maven or build tools
  FROM  eclipse-temurin:17-jre
        
  #Sets the working directory inside the container
  WORKDIR  /app
        
  #Copies only the JAR file from the first stage
  #Excludes source code and build dependencies
  COPY  --from=builder  /app/target/demo-0.0.1-SNAPSHOT.jar  app.jar
    
  #Documents the application listening port
  EXPOSE  8080
    
  #Runs the application when the container starts
  CMD  ["java",  "-jar",  "app.jar"]
    
#--------------------------------------------#
#------ Stage 2 : Run the Application -------#
#--------------------------------------------#
    
   #Uses a lightweight Java 17 runtime image
   #Does NOT include Maven or build tools
   FROM  eclipse-temurin:17-jre
    
   #Sets the working directory inside the container
   WORKDIR  /app
    
   #Copies only the JAR file from the first stage
   #Excludes source code and build dependencies
   COPY  --from=builder  /app/target/demo-0.0.1-SNAPSHOT.jar  app.jar
    
   #Documents the application listening port
   EXPOSE  8080
    
   #Runs the application when the container starts
   CMD  ["java",  "-jar",  "app.jar"]
   ```
## Step 3: Build the Docker Image

The Docker image was built using:

    docker build -t app3 .

📌 The image size was observed and compared with previous labs.
## Step 4: Run a Container from the Image

A container named `container3` was started:

    docker run -d -p 8080:8080 --name container3 app3

Verification:

    docker ps -a
    
## Step 5: Test the Application

    curl http://localhost:8080
## Step 6: Stop and Remove the Container

After testing, the container was stopped and removed:
```
docker stop container3
docker rm container3
```

Verification:
```
docker ps
docker ps -a
```
## 🧪 Lab 3 – Single-Stage Docker Build (Build Inside Container)

### What we did

-   Used a **Maven + JDK image**
    
-   Built the application **inside the container**
    
-   Produced a runnable image in one stage
    
# Conclusion

## Comparing image sizes

<img width="906" height="75" alt="image" src="https://github.com/user-attachments/assets/e449bc92-a45c-4f27-a7ad-76dca920f7db" />

### Key takeaway

> Building applications in a single-stage Docker image results in **very large images** because build tools (Maven, source code, caches) are shipped into production.

----------

## 🧪 Lab 4 – Runtime-Only Docker Image (Pre-built JAR)

### What we did

-   Built the application **outside Docker**
    
-   Used a **Java runtime-only image**
    
-   Copied only the JAR file into the container
    

### Key takeaway

> Separating build and runtime reduces image size significantly, but requires the build process to happen outside Docker.
    

----------

## 🧪 Lab 5 – Multi-Stage Docker Build (Best Practice)

### What we did

-   Used **two Docker stages**:
    
    -   Build stage (Maven + JDK)
        
    -   Runtime stage (JRE only)
        
-   Copied the JAR from the build stage into the runtime stage
    

### Key takeaway

> Multi-stage builds provide the best of both worlds: build inside Docker while keeping the final image small and production-ready.

