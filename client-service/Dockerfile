FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/client-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-DappName=client-service", "-jar", "app.jar"]