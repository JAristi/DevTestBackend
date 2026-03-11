FROM eclipse-temurin:17-jdk

# Create a directory for the app
WORKDIR /app

# Copy the JAR ()
COPY target/franchise-api-0.0.1-SNAPSHOT.jar app.jar

# Execute app
ENTRYPOINT ["sh", "-c", "sleep 15 && java -jar app.jar"]