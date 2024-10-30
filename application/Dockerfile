FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ms-products-0.0.1.jar
COPY ${JAR_FILE} ms-products.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ms-products.jar"]