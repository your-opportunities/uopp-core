# -------- Build stage --------
FROM gradle:8.7-jdk17 AS build

WORKDIR /app

# Copy only files needed for dependency resolution first
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# Download dependencies first (better caching)
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the rest of the source
COPY src/ src/

# Build the application (skip tests for speed in Docker)
RUN ./gradlew build -x test --no-daemon

# -------- Runtime stage --------
FROM eclipse-temurin:17-jdk-jammy AS runtime
# eclipse-temurin images are smaller & well-maintained

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Use a more production-ready Java command with memory settings
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
