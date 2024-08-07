# service/Dockerfile

FROM postgres:latest

# Copy the SQL scripts to the Docker image
COPY src/main/resources/db/postgres/01-schema-postgresql.sql /docker-entrypoint-initdb.d/
COPY src/main/resources/db/postgres/02-data-postgresql.sql /docker-entrypoint-initdb.d/
