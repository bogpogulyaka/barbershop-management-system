FROM openjdk:11-slim

WORKDIR /opt/server
COPY ./target/service-barber-services-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "server.jar"]

