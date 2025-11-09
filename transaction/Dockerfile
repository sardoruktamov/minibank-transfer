# For Java 17, try this
#FROM openjdk:17-jdk-slim-buster
FROM eclipse-temurin:17-jdk-jammy

COPY  target/transaction-0.0.1-SNAPSHOT.jar /transaction.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","transaction.jar"]

