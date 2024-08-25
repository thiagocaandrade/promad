# Use a base image with Java and Maven
FROM maven:3.8.1-openjdk-17

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file into the container
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the application into the container
COPY src ./src

# Build the application
RUN mvn package

# Set the startup command
CMD ["java", "-jar", "target/promad-0.0.1-SNAPSHOT.jar"]