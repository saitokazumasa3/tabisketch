# TailwindCSSのビルドステージ
FROM node:21 AS tailwindcss-build
WORKDIR /app
COPY src ./src
COPY tailwind.config.js .
COPY package.json .
COPY package-lock.json .
# TailwindCSSのビルド
RUN npm install
RUN npx tailwindcss -i src/main/resources/static/css/input.css -o src/main/resources/static/css/tailwind.css


# Spring Bootアプリケーションのビルドステージ
FROM maven:3.9.9-eclipse-temurin-22 AS spring-boot-build
WORKDIR /app
COPY pom.xml .
COPY --from=tailwindcss-build /app/src ./src
# アプリケーションのパッケージング（テストをスキップ）
RUN mvn package -DskipTests


# アプリケーション実行
FROM eclipse-temurin:22-jdk AS app-runtime
WORKDIR /app
COPY --from=spring-boot-build /app/target/tabisketch-0.0.1-SNAPSHOT.jar /app/app.jar
# 環境変数の設定
ARG _DATABASE_URL
ARG _DATABASE_USERNAME
ARG _DATABASE_PASSWORD
ARG _GOOGLE_MAPS_API_KEY
ARG _MAIL_PASSWORD
ARG _MAIL_USERNAME
ENV DATABASE_URL=${_DATABASE_URL}
ENV DATABASE_USERNAME=${_DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${_DATABASE_PASSWORD}
ENV GOOGLE_MAPS_API_KEY=${_GOOGLE_MAPS_API_KEY}
ENV MAIL_PASSWORD=${_MAIL_PASSWORD}
ENV MAIL_USERNAME=${_MAIL_USERNAME}
# アプリケーションの起動コマンド
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
