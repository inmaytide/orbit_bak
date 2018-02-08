FROM java:9

VOLUME /tmp

RUN ln -s lib /usr/lib/jvm/java-9-openjdk-amd64/conf

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT  java \
			--add-opens java.base/java.util=ALL-UNNAMED \
			--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
			--add-opens java.base/java.text=ALL-UNNAMED \
			--add-opens java.desktop/java.awt.font=ALL-UNNAMED \
			--add-modules java.xml.bind \
			-Djava.security.egd=file:/dev/./urandom -jar app.jar
			
EXPOSE 7001
