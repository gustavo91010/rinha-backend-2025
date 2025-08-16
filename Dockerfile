# Build stage com GraalVM + native-image (JDK 24)
FROM ghcr.io/graalvm/native-image-community:24 AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw -Pnative native:compile -DskipTests

# Run stage leve
FROM debian:bookworm-slim
WORKDIR /app

COPY --from=build /app/target/rinha-de-backend-2025 app
RUN chmod +x ./app

EXPOSE 8080
CMD ["./app"]



# # ​​ Build stage com GraalVM + native-image (JDK 24)
# FROM ghcr.io/graalvm/native-image-community:24 AS build
# WORKDIR /app

# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# COPY src ./src

# RUN chmod +x mvnw
# RUN ./mvnw clean package -DskipTests -Pnative

# RUN native-image -jar target/*.jar app

# # ​​ Run stage leve
# FROM alpine:3.18
# WORKDIR /app

# COPY --from=build /app/app .

# EXPOSE 8080
# CMD ["./app"]

# # Build stage with GraalVM
# FROM ghcr.io/graalvm/native-image-community:21 AS build
# WORKDIR /app

# # Install necessary build tools
# RUN microdnf install findutils git gcc glibc-devel zlib-devel

# # Copy project files
# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# COPY src ./src

# # Give permission to mvnw
# RUN chmod +x mvnw

# # Build native image
# RUN ./mvnw clean package -DskipTests -Pnative

# # Run stage (using a minimal base image)
# FROM alpine:3.18
# WORKDIR /app

# # Copy the native executable from the build stage
# COPY --from=build /app/target/*-runner /app/application

# EXPOSE 8080

# # Run the application
# CMD ["./application"]

# # Build stage
# FROM eclipse-temurin:21-jdk-alpine AS build
# WORKDIR /app

# # Copia arquivos do projeto
# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# COPY src ./src

# # Dá permissão para o mvnw
# RUN chmod +x mvnw

# # Builda o projeto sem testes
# RUN ./mvnw clean package -DskipTests

# # Run stage
# FROM eclipse-temurin:21-jre-alpine
# WORKDIR /app

# # Copia só o jar do build anterior
# COPY --from=build /app/target/*.jar app.jar

# EXPOSE 8080

# # CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
# # CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
# CMD java $JAVA_OPTS -jar app.jar


