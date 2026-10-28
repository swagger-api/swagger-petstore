FROM eclipse-temurin:17-jre

WORKDIR /swagger-petstore

COPY target/jetty-home /opt/jetty-home
RUN java -jar /opt/jetty-home/start.jar --add-modules=http,ee8-deploy,requestlog
COPY target/*.war /swagger-petstore/webapps/ROOT.war
COPY src/main/resources/openapi.yaml /swagger-petstore/openapi.yaml
COPY inflector.yaml /swagger-petstore/

EXPOSE 8080

CMD ["java", "-DswaggerUrl=openapi.yaml", "-jar", "/opt/jetty-home/start.jar", "jetty.requestlog.filePath=/var/log/yyyy_mm_dd-requests.log"]
