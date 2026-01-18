# Lab 4 – Run Java Spring Boot App in a Container (Using Pre-built JAR)

## Objective

The objective of this lab is to **run a Java Spring Boot application inside a Docker container using a pre-built JAR file**, instead of building the application inside the container.
## Step 1: Clone the Application Code

The application source code was cloned from GitHub:

`git clone https://github.com/Ibrahim-Adel15/Docker-1.git` 

This repository contains:

-   `pom.xml` → Maven configuration
    
-   `src/` → Java source code
 
## Step 2: Build the Application (Outside Docker)

The application was built **on the host machine** using Maven:

`mvn clean package` 

This command:

-   Compiles the source code
    
-   Runs tests
    
-   Generates the JAR file in the `target/` directory
    

📌 This step ensures Docker only handles **runtime**, not **build**, making the image smaller.
## Step 3: Write the Dockerfile

A `Dockerfile` was created to run the already-built JAR file.

    #Uses a Java 17 runtime-only image
    #Smaller than Maven images (no build tools included)
    FROM  eclipse-temurin:17-jre
    
    #Sets /app as the working directory inside the container
    WORKDIR  /app
    
    #Copies the JAR file from the host into the container
    COPY  target/demo-0.0.1-SNAPSHOT.jar  app.jar
    
    #Documents that the application listens on port 8080
    EXPOSE  8080
      
    #Runs the Spring Boot application when the container starts
    CMD  ["java",  "-jar",  "app.jar"]

## Step 4: Build the Docker Image

The Docker image was built using:

`docker build -t app2 .` 
-   The image size was observed and compared with previous labs. Using a runtime-only image significantly reduces image size
## Step 5: Run a Container from the Image

A container named `container2` was started from the image:

`docker run -d -p 8080:8080 --name container2 app2`
To verify:

`docker ps -a`
## Step 6: Test the Application

The application was tested locally using:

`curl http://localhost:8080`

## Step 7: Stop and Remove the Container

After testing, the container was stopped and removed:

```
docker stop container2
docker rm container2
``` 

Verification:

``` 
docker ps
docker ps -a
```
