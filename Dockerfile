# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia arquivos do projeto
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Dá permissão para o mvnw
RUN chmod +x mvnw

# Builda o projeto sem testes
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia só o jar do build anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
# CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
CMD java $JAVA_OPTS -jar app.jar


