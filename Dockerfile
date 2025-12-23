FROM eclipse-temurin:21-jdk
ARG JAR_FILE=target/integraobra-api-rest-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} integraobra-api-rest.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","integraobra-api-rest.jar"]