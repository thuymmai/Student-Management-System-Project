# Use a base image with eclipse-temurin Java 21 installed
FROM eclipse-temurin

LABEL maintainer="thuymmai11@gmail.com"

# Set the working directory in the container
WORKDIR /myapp

# Copy the packaged Spring Boot application JAR file into the container
COPY target/studentmanagement-0.0.1-SNAPSHOT.jar /myapp/studentmanagement-docker.jar

ENTRYPOINT ["java", "-jar", "studentmanagement-docker.jar"]