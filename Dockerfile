#Build with maven
FROM eclipse-temurin:17-jdk AS build

# Create a directory for the app
WORKDIR /app

# Install maven to Render
# RUN apt-get update && apt-get install -y maven

# Copy the POM and SRC () to Render
# COPY pom.xml .
# COPY src ./src

# Execute the clean package without tests to Render
# RUN mvn clean package -DskipTests

# Final image to Render
# FROM eclipse-temurin:17-jdk
# WORKDIR /app

# Copy the JAR to Render
#COPY --from=build /app/target/franchise-api-0.0.1-SNAPSHOT.jar app.jar

# Copy the JAR
COPY target/franchise-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Execute app
ENTRYPOINT ["sh", "-c", "sleep 45 && java -jar app.jar"]