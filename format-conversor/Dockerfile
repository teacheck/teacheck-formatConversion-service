FROM openjdk:8-jre-slim

ENV VERTICLE_FILE target/format-conversor-1.0-SNAPSHOT-fat.jar
ENV VERTICLE_HOME /usr/verticles

COPY $VERTICLE_FILE $VERTICLE_HOME/format-conversor-1.0-fat.jar

WORKDIR $VERTICLE_HOME
CMD ["java", \
        "-XshowSettings:vm", \
        "-XX:+UnlockExperimentalVMOptions", \
        "-XX:+UseCGroupMemoryLimitForHeap", \
        "-XX:MaxRAMFraction=2", \
        "-server", \
        "-jar", "format-conversor-1.0-fat.jar"]

EXPOSE 8080
