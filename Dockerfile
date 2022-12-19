# syntax = docker/dockerfile:1.2

FROM openjdk:17 AS builder

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod +x mvnw
RUN ./mvnw clean package

ARG SECRET_KEY
ARG STRIPE_SECRET_KEY

FROM openjdk:17

COPY --from=builder target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]