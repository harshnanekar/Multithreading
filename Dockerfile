# 1️⃣ Use an official OpenJDK runtime as a base image
FROM openjdk:21-jdk-slim

# 2️⃣ Set the working directory inside the container
WORKDIR /app

# 3️⃣ Copy the built JAR file into the container
COPY target/*.jar app.jar

# 4️⃣ Expose the application port (Spring Boot default: 8080)
EXPOSE 8080

# 5️⃣ Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

