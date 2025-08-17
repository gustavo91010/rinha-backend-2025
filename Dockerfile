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

