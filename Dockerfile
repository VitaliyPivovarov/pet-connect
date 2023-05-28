FROM openjdk:17-alpine AS final
COPY ./build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]