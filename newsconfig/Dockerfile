FROM openjdk:11-slim as build
COPY target/newsconfig-0.0.1-SNAPSHOT.jar newsconfig-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/newsconfig-0.0.1-SNAPSHOT.jar"]