FROM openjdk:11-slim as build
COPY target/newapi-0.0.1-SNAPSHOT.jar newapi-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/newapi-0.0.1-SNAPSHOT.jar"]