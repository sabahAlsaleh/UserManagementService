# Use your base image
FROM openjdk:17-jdk-slim

# Copy your application JAR and the wait-for-it script
COPY target/*.jar /app.jar
COPY wait-for-it.sh /wait-for-it.sh

# Make the script executable
RUN chmod +x /wait-for-it.sh

# Specify the entry point
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app.jar"]



#FROM openjdk:17-jdk-slim
#EXPOSE 8081
#ARG JAR_FILE=target/*.jar
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
