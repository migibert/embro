FROM openjdk:21-jdk

COPY target/embro-0.0.1-SNAPSHOT.jar embro-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/embro-api.jar"]
