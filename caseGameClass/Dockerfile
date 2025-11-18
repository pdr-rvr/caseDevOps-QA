FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /educacaoGamificada
COPY target/caseGameClass-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your application will run on
EXPOSE 8585

# Specify the command to run on container start
CMD ["java", "-jar", "caseGameClass-0.0.1-SNAPSHOT.jar"]
