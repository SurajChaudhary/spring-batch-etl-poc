# spring-batch-etl-poc
This POC shows example of spring batch app for loading data from database, transforming it and then posting it to another service using rest template.

docker run --name postgresDb -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:alpine

Prometheus: https://stackoverflow.com/questions/48704789/how-to-measure-service-methods-using-spring-boot-2-and-micrometer

