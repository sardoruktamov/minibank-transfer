# For Java 17, try this
FROM openjdk:17-jdk-slim-buster

COPY  target/profile-0.0.1-SNAPSHOT.jar /profile.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","profile.jar"]

