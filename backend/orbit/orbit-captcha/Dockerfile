FROM java:9

VOLUME /tmp

RUN ln -s lib /usr/lib/jvm/java-9-openjdk-amd64/conf
RUN git clone https://github.com/vishnubob/wait-for-it.git
RUN apt-get install -y libfontconfig

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT  ./wait-for-it/wait-for-it.sh -t 0 -s -h orbit-discovery -p 7000 \
            && \
            java \
			--add-opens java.base/java.util=ALL-UNNAMED \
			--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
			--add-opens java.base/java.text=ALL-UNNAMED \
			--add-opens java.desktop/java.awt.font=ALL-UNNAMED \
			--add-modules java.xml.bind \
			-Djava.security.egd=file:/dev/./urandom -jar app.jar