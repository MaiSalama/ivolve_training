# Use Maven with Java 17
FROM maven:3.9.6-eclipse-temurin-17

# Create working directory
WORKDIR /app

# Copy source code into container
COPY . .

# Build the application
RUN mvn clean package

# Expose application port
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]