# Stage 1: Build the application
FROM amazoncorretto:17-alpine-jdk AS build

# Frissítsd az Alpine Linux csomagkezelőt és telepítsd a szükséges eszközöket
RUN apk update && apk add --no-cache maven

WORKDIR /app

# Másold a forráskódot a konténerbe
COPY . .

# Építsd meg a JAR fájlt
RUN mvn clean package

# Stage 2: Create the runtime image
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

# Másold át a JAR fájlt a build fázisból a runtime fázisba
COPY --from=build /app/web/target/web-1.0-SNAPSHOT.jar /app/web.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/web.jar"]
