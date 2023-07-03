FROM openjdk:8-jre-alpine

WORKDIR /swagger-petstore

COPY target/lib/jetty-runner.jar /swagger-petstore/jetty-runner.jar
COPY target/*.war /swagger-petstore/server.war

EXPOSE 8080

CMD ["java", "-jar",  "/swagger-petstore/jetty-runner.jar", "--log", "/var/log/yyyy_mm_dd-requests.log", "/swagger-petstore/server.war"]
