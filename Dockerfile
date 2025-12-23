# Etapa 1: Construcción (Maven)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jdk
WORKDIR /app
# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/target/integraobra-api-rest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]