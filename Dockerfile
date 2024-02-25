FROM openjdk:11-jdk-slim
COPY build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "-X"]