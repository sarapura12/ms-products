FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*

ENV JDBC_DATABASE_URL='jdbc:mysql://msproductsDB/productsDB?createDatabaseIfNotExist=true&autoReconnect=true&serverTimeZone=GMT-3'
ENV JDBC_DATABASE_USERNAME=root
ENV JDBC_DATABASE_PASSWORD=admin

ARG JAR_FILE=target/ms-products-0.0.2.jar
COPY ${JAR_FILE} ms-products.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ms-products.jar"]