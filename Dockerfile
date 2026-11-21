FROM eclipse-temurin:17-jre AS jetty

WORKDIR /swagger-petstore

COPY target/jetty-home /opt/jetty-home
# enable the modules, then drop every jetty-home jar they don't load
RUN java -jar /opt/jetty-home/start.jar --add-modules=http,ee8-deploy,requestlog \
    && java -jar /opt/jetty-home/start.jar --list-config \
        | grep -oE '\$\{jetty\.home\}/lib/[^ ]+\.jar' | sed 's#^\${jetty\.home}#/opt/jetty-home#' | sort -u > /tmp/keep \
    && test -s /tmp/keep \
    && find /opt/jetty-home/lib -name '*.jar' | { grep -vxFf /tmp/keep || true; } | xargs rm -f \
    && find /opt/jetty-home/lib -type d -empty -delete \
    && rm -f /opt/jetty-home/*-cyclonedx.*

FROM eclipse-temurin:17-jre

WORKDIR /swagger-petstore

COPY --from=jetty /opt/jetty-home /opt/jetty-home
COPY --from=jetty /swagger-petstore /swagger-petstore
COPY target/*.war /swagger-petstore/webapps/ROOT.war
COPY src/main/resources/openapi.yaml /swagger-petstore/openapi.yaml
COPY inflector.yaml /swagger-petstore/

EXPOSE 8080

CMD ["java", "-DswaggerUrl=openapi.yaml", "-jar", "/opt/jetty-home/start.jar", "jetty.requestlog.filePath=/var/log/yyyy_mm_dd-requests.log"]
