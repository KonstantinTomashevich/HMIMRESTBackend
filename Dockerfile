# docker build -t HMIMRestBackend .
# docker run -t HMIMRestBackend
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/HMIMRestBackend-1.0.0-SNAPSHOT.jar HMIMRestBackend-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/HMIMRestBackend-1.0.0-SNAPSHOT.jar"]
EXPOSE 8080:8080