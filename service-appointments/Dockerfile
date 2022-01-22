FROM openjdk:11-slim

WORKDIR /opt/server
COPY ./target/service-appointments-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "server.jar"]

