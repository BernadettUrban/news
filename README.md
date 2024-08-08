docker-compose up to start postgres

and on windows
winpty docker exec -it news-service-db-1 pg_isready -h localhost -p 5432 -U postgres


to access the db

path to swagger documentation: http://localhost:8080/swagger-ui/index.html#/news-rest-controller

