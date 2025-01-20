# tailwindcssをビルドするためのステージ
FROM node:21 AS tailwindcss
WORKDIR /app
COPY package-lock.json package.json ./
RUN npm ci
COPY tailwind.config.js .
COPY src/ ./src/
RUN npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./tailwind.css


# アプリケーションをビルドするためのステージ
FROM maven:3.9-eclipse-temurin-22 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
COPY --from=tailwindcss /app/tailwind.css /app/src/main/resources/static/css/
RUN mvn clean package -DskipTests=true


# JARを実行するためのステージ
FROM eclipse-temurin:22 AS prod
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
