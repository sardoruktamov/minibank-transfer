# For Java 17, try this
FROM openjdk:17-jdk-slim-buster

COPY  target/notification-0.0.1-SNAPSHOT.jar /notification.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","notification.jar"]

