# Use the official OpenJDK 21 image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build file and the source code to the container
COPY target/amacame-0.0.1-SNAPSHOT.jar .

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["sh", "-c", "until nc -z amacame_db 5432; do echo waiting for database; sleep 2; done; java -jar app.jar"]
CMD ["java", "-jar", "amacame-0.0.1-SNAPSHOT.jar"]