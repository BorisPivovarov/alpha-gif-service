FROM openjdk:16
EXPOSE 8080
ADD out/artifacts/alpha_gif_service_jar/alpha-gif-service.jar alpha-gif-service.jar
ENTRYPOINT ["java","-jar","alpha-gif-service.jar"]