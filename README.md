# My Spring Boot Application

## Description

This project is a Spring Boot application that utilizes Docker for containerization. It includes Swagger documentation for easy access to the API endpoints.

## Prerequisites

- Docker
- Docker Compose

## Getting Started

1. **Clone the Repository**

   ```
   git clone https://github.com/BernadettUrban/news.git
   cd news
    ```

2. **Build and Run the Application**

    Ensure Docker and Docker Compose are installed on your machine. Then, run the following command to start the application:

    ```
    docker-compose up
    ```

    This command will build the Docker image (if needed) and start the containers defined in the docker-compose.yml file.

3. **Access Swagger Documentation**

    Once the application is running, you can access the Swagger documentation at:

    http://localhost:8080/swagger-ui/index.html

##Configuration
If you need to configure the application or make changes to the Docker setup, refer to the docker-compose.yml file and the application properties files located in src/main/resources.

##Troubleshooting
Application Not Starting: Check the logs for any errors. Ensure Docker is running and that there are no port conflicts on localhost:8080.
Swagger Documentation Not Accessible: Ensure the application is running correctly and that you have navigated to the correct URL.