# docker build . -t hmim-rest-backend --build-arg APP_DB_URL --build-arg APP_DB_USER --build-arg APP_DB_PASSWORD && docker run hmim-rest-backend
FROM openjdk:8-jdk-alpine
VOLUME /tmp

ARG APP_DB_URL
ARG APP_DB_USER
ARG APP_DB_PASSWORD

ENV DB_URL=${APP_DB_URL}
ENV DB_USER=${APP_DB_USER}
ENV DB_PASSWORD=${APP_DB_PASSWORD}

COPY target/HMIMRestBackend-1.0.0-SNAPSHOT.jar HMIMRestBackend-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/HMIMRestBackend-1.0.0-SNAPSHOT.jar"]
EXPOSE 8080:8080