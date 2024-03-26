FROM openjdk:17-alpine

WORKDIR /app
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

RUN mkdir -p resources && \
    mkdir -p resources/images && \
    mkdir -p resources/logs

ENTRYPOINT ["nohup", "java", "-jar", "app.jar", "&"]
CMD ["--spring.profiles.active=dev"]
