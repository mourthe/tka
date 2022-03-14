FROM openjdk:11
EXPOSE 8080
ADD /build/libs/take-home.assignment-0.0.1-SNAPSHOT.jar take-home.assignment.jar
ENTRYPOINT ["java", "-jar", "take-home.assignment.jar"]