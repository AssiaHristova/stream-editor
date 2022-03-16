FROM maven as builder

COPY . /project
WORKDIR /project
RUN mvn --settings /usr/share/maven/ref/settings-docker.xml clean install -Dmaven.test.skip=true
RUN mkdir -p /project/target/stream-editor

FROM openjdk:17 as runtime

WORKDIR /tmp/
RUN mkdir stream-editor
COPY --from=builder /project/target/stream-editor stream-editor

WORKDIR /app
COPY --from=builder /project/target/stream-editor-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "stream-editor-0.0.1-SNAPSHOT.jar"]