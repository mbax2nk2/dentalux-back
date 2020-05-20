FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","${JAVA_OPTS}","-jar","/app.jar"]