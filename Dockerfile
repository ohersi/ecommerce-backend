# syntax = docker/dockerfile:1.2

FROM openjdk:17 AS builder

ARG SECRET_KEY
ARG STRIPE_SECRET_KEY
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod +x mvnw
RUN ./mvnw clean package



FROM openjdk:17

COPY --from=builder target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]