#--------------------------------------------#
#----- Stage 1 : Build the Application ------#
#--------------------------------------------#

#Uses a Maven image with Java 17
#Contains all tools required to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

#Sets the working directory inside the container
WORKDIR /app

#Copies the application source code into the container
COPY . .

#Builds the application
#Generates the JAR file in target/
RUN mvn clean package

#--------------------------------------------#
#------ Stage 1 : Run the Application -------#
#--------------------------------------------#

#Uses a lightweight Java 17 runtime image
#Does NOT include Maven or build tools
FROM eclipse-temurin:17-jre

#Sets the working directory inside the container
WORKDIR /app

#Copies only the JAR file from the first stage
#Excludes source code and build dependencies
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

#Documents the application listening port
EXPOSE 8080

#Runs the application when the container starts
CMD ["java", "-jar", "app.jar"]





