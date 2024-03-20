FROM openjdk:17-alpine

WORKDIR /app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/StepStory-Server-0.0.1-SNAPSHOT.jar ./app.jar

CMD ["mkdir", "resources"]
CMD ["mkdir", "./resources/images"]
CMD ["mkdir", "./resources/logs"]
CMD ["java","-jar","./app.jar","--spring.profiles.active=dev"]