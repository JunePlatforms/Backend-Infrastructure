# Use a base image with Java (e.g., OpenJDK)
FROM amazoncorretto:17-alpine

# Copy the JAR file from the 'target' directory of your project into the container
COPY ./target/CourierNetwork-0.0.1-SNAPSHOT.jar app.jar

# Define the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]
