# Step 1: Use a JDK image to build the JAR
FROM eclipse-temurin:17-jdk AS builder

# Set working directory
WORKDIR /app

# Copy everything
COPY . .

# Build the JAR using Maven wrapper
RUN ./mvnw -B package -DskipTests

# Step 2: Use a clean JRE image to run JAR
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/simpleCrudApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
