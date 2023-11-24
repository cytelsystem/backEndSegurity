FROM openjdk:18-bullseye
ARG JAR_FILE=./target/api-event-0.0.1-SNAPSHOT.jar
ARG JAR_DEPLOYED=/opt/master_events/api-event-0.0.1-SNAPSHOT.jar
WORKDIR /opt/master_events
COPY ${JAR_FILE} ${JAR_DEPLOYED}