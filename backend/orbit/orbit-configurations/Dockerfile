FROM java:9

VOLUME /tmp

RUN ln -s lib /usr/lib/jvm/java-9-openjdk-amd64/conf

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

EXPOSE 7000
