# For Java 17, try this
FROM openjdk:17-jdk-slim-buster

COPY  target/eurekaserver-0.0.1-SNAPSHOT.jar /eurekaserver.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","eurekaserver.jar"]

