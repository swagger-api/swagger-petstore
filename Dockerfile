# Build stage
FROM maven:3.8.6-openjdk-11-slim AS build

RUN apt-get update && apt-get install -y git
# RUN git clone 
RUN mvn -version
WORKDIR /home/app/
COPY src ./src/  
COPY pom.xml ./pom.xml 
RUN ls -ltr
RUN pwd
RUN ls -ltr ./src/
RUN mvn -f pom.xml clean install -DskipTests=true

# FROM openjdk:8-jre-alpine
FROM eclipse-temurin:11-jre-alpine

WORKDIR /swagger-petstore

COPY FROM:build/target/lib/jetty-runner.jar /swagger-petstore/jetty-runner.jar
COPY target/*.war /swagger-petstore/server.war
COPY src/main/resources/openapi.yaml /swagger-petstore/openapi.yaml
COPY inflector.yaml /swagger-petstore/

EXPOSE 8080

CMD ["java", "-jar", "-DswaggerUrl=openapi.yaml", "/swagger-petstore/jetty-runner.jar", "--log", "/var/log/yyyy_mm_dd-requests.log", "/swagger-petstore/server.war"]
