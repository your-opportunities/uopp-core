FROM openjdk:17-oracle

COPY /build/libs/uopp-core-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
