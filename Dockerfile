FROM openjdk:17-alpine AS build
WORKDIR /
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]