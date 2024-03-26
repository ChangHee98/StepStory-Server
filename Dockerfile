FROM openjdk:17-alpine


WORKDIR /backend
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar


ENTRYPOINT ["nohup", "java", "-jar", "app.jar", "&", --spring.profiles.active=dev"]
CMD ["mkdir", "resources"]
CMD ["mkdir", "./resources/images"]
CMD ["mkdir", "./resources/logs"]
