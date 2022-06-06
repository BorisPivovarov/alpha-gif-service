FROM openjdk:17
EXPOSE 8080
ADD build/libs/alpha-gif-service-0.0.1-SNAPSHOT-plain.jar alpha-gif-service-0.0.1-SNAPSHOT-plain.jar
ENTRYPOINT ["java","-jar","alpha-gif-service-0.0.1-SNAPSHOT-plain.jar"]