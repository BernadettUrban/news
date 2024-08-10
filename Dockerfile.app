# Stage 1: Build the application
FROM amazoncorretto:17-alpine-jdk AS build

# Update Alpine Linux package manager and install necessary tools
RUN apk update && apk add --no-cache maven

WORKDIR /app

# Copy the source code into the container
COPY . .

# List the contents of the /app directory to check what was copied
RUN ls -al /app

# Build the JAR file
RUN mvn clean package -DskipTests

# List the contents of the /app directory after building to verify the JAR file is created
# List contents of the target directory after building
RUN ls -al /app/web/target

# List contents of the JAR file
RUN jar tf /app/web/target/web-1.0-SNAPSHOT.jar

# Stage 2: Create the runtime image
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/web/target/web-1.0-SNAPSHOT.jar /app/web.jar



# Run the application
ENTRYPOINT ["java", "-jar", "/app/web.jar"]

# Expose the port the app runs on
EXPOSE 8080
