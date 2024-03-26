FROM openjdk:17-alpine

WORKDIR /app
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

RUN mkdir -p resources && \
    mkdir -p resources/images && \
    mkdir -p resources/logs

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
