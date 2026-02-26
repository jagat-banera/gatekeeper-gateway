FROM eclipse-temurin:17-jre-alpine
LABEL authors="JAGAT"
WORKDIR /gatekeeper-gateway
COPY target/gatekeeper-*.jar app.jar
EXPOSE 8080
EXPOSE 8082
ENTRYPOINT ["java" , "-jar" , "app.jar"]