FROM openjdk:11-slim

WORKDIR /opt/server
COPY ./target/service-barber-feedback-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "server.jar"]

