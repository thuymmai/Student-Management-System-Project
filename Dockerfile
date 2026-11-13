FROM eclipse-temurin:21

LABEL maintainer="thuymmai11@gmail.com"

WORKDIR /app

COPY target/studentmanagement-0.0.1-SNAPSHOT.jar /app/studentmanagement-docker.jar

ENTRYPOINT["java", "-jar", "studentmanagement-docker.jar"]