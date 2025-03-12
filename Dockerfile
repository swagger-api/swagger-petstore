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

ENV JAVA_TOOL_OPTIONS="-javaagent:/swagger-petstore/otel-javaagent.jar" \
    OTEL_JAVAAGENT_ENABLED=true \
    OTEL_TRACES_EXPORTER=otlp \
    OTEL_METRICS_EXPORTER=none \
    OTEL_LOGS_EXPORTER=none \
    OTEL_RESOURCE_ATTRIBUTES="service.name=swagger-petstore,deployment.environment=prod" \
    OTEL_EXPORTER_OTLP_ENDPOINT="https://$BUGSNAG_API_KEY.otlp.bugsnag.com" \
    OTEL_JAVAAGENT_LOGGING=simple

# Run Swagger Petstore with OpenTelemetry
CMD ["sh", "-c", \
    "exec java $JAVA_TOOL_OPTIONS -jar -DswaggerUrl=openapi.yaml \
    /swagger-petstore/jetty-runner.jar --log /var/log/yyyy_mm_dd-requests.log /swagger-petstore/server.war"]
