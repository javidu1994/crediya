# ==============================
# Etapa 1: Compilación
# ==============================
FROM gradle:9.1.0-jdk25 AS builder
WORKDIR /workspace
COPY . .
RUN gradle clean :app-service:bootJar --no-daemon

# ==============================
# Etapa 2: Imagen final
# ==============================
FROM eclipse-temurin:25-jre-alpine AS runtime
WORKDIR /app
COPY --from=builder /workspace/applications/app-service/build/libs/*.jar crediya-ms-solicitudes.jar
EXPOSE 9080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "/app/crediya-ms-solicitudes.jar"]


