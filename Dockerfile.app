# Dockerfile.app
FROM openjdk:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file from the web module's target directory
COPY web/target/web-1.0-SNAPSHOT.jar /app/web.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/web.jar"]
