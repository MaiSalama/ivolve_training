### 1. Clone the Source Code

We cloned an existing Java Maven project from GitHub:

    bash git clone https://github.com/Ibrahim-Adel15/Docker-1.git


This repository contains:

 - pom.xml → Maven configuration file
 - src/ → Java source code

These files are required to build the application.

### 2. Write Dockerfile.
	

Create a file called Dockerfile in the same directory that contains pom.xml and src/

   

 

    #Uses an official Maven image with Java 17
    FROM maven:3.9.6-eclipse-temurin-17

    #Create /app as the working directory inside the container
	WORKDIR /app

    #Copies the entire project (source code + pom.xml) into the container
	COPY . .

    #Builds the Java application
    #Generates a JAR file inside `target/`
	RUN mvn clean package

    #Expose application port
	EXPOSE 8080

    #Starts the Java application when the container runs
	CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]

### 3. Build app1 Image.
	
The Docker build initially failed because Docker Desktop was not running. 
	After starting Docker Desktop and ensuring the Docker Engine was active, the image build executed successfully.
	a. Open Docker Desktop then, wait untill you see -> Docker Desktop is running
	b. Verify docker engine is running -> docker info
c. Build the image 

     docker build -t app1 .
Dockerfile must be in the same directory where you run docker build .

### 4. Run container1 from app1 Image.
	
    docker run -d -p 8080:8080 --name container1 app1

to show all containers run :

    docker ps -a

### 5. Test the application.
	curl http://localhost:8080

### 6. Stop and delete the container
	docker stop container1
	docker ps 
	docker ps -a
	docker rm container1
	docker ps 
	docker ps -a
	



    
