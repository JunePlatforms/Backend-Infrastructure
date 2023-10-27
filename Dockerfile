# Use a base image with Java (e.g., OpenJDK)
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the 'target' directory of your project into the container
COPY ./target/CourierNetwork-0.0.1-SNAPSHOT.jar CourierNetwork-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot application listens on (if applicable)
EXPOSE 8080

# Define the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "CourierNetwork-0.0.1-SNAPSHOT.jar"]
