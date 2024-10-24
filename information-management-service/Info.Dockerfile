# Use a base image with GraalVM and Java 17
FROM findepi/graalvm:java17
# Set the working directory within the container
WORKDIR /app
#EXPOSE 8761
# Copy your application JAR file to the container
COPY ./target/information-management-service-0.0.1-SNAPSHOT.jar /app

# Copy the application.properties to the container
COPY ./src/main/resources/application-docker.properties /app
#"--spring.config.location=/app/application-docker.properties"
# Define the entry point to run your Java application with the external properties file
ENTRYPOINT ["java", "-jar", "information-management-service-0.0.1-SNAPSHOT.jar", "--spring.config.location=/app/application-docker.properties" ]
