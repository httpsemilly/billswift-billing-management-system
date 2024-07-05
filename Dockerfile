# Stage 1: Construção do projeto com Maven
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven

WORKDIR /app

COPY . .

# Ignora os testes durante a construção
RUN mvn clean install -DskipTests

# Stage 2: Criação da imagem final
FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/billswift-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar"]
