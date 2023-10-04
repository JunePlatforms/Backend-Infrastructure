FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/*.jar CourierNetwork-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080