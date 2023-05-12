FROM openjdk:8-jdk-alpine
# Add a volume pointing to /tmp
VOLUME /tmp
# The application's jar file
ARG JAR_FILE=target/microserviceGestionCedit-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} microserviceGestionCedit.jar
# Expose port 8080
EXPOSE 8085
# Run the jar file
ENTRYPOINT ["java", "-jar", "/microserviceGestionCedit.jar"]