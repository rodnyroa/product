FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG NAME
RUN echo "NAME:${NAME}"
ARG TAG
RUN echo "tag:${TAG}"
ARG JAR_FILE=target/${NAME}-${TAG}.jar
ADD ${JAR_FILE} my-app.jar
ENV JAVA_OPTS="-XX:+UseG1GC -Xmx2g -Xms32m"
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar my-app.jar
