FROM openjdk:11-jre-slim

WORKDIR /swagger-petstore

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

RUN curl -Lo /swagger-petstore/otel-javaagent.jar \
    "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar"

COPY target/lib/jetty-runner.jar /swagger-petstore/jetty-runner.jar
COPY target/*.war /swagger-petstore/server.war
COPY src/main/resources/openapi.yaml /swagger-petstore/openapi.yaml
COPY inflector.yaml /swagger-petstore/

EXPOSE 8080

ENV OTEL_SERVICE_NAME=swagger-petstore \
    OTEL_EXPORTER_OTLP_ENDPOINT=https://otlp.bugsnag.com \
    OTEL_LOGS_EXPORTER=otlp \
    OTEL_RESOURCE_ATTRIBUTES="deployment.environment=prod" \
    OTEL_EXPORTER_LOGGING_ENABLED=true

CMD ["sh", "-c", \
    "exec java -javaagent:/swagger-petstore/otel-javaagent.jar -jar -DswaggerUrl=openapi.yaml \
    -DOTEL_EXPORTER_OTLP_HEADERS=\"Authorization=Api-Key $BUGSNAG_API_KEY\" \
    /swagger-petstore/jetty-runner.jar --log /var/log/yyyy_mm_dd-requests.log /swagger-petstore/server.war"]
