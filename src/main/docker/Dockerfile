FROM registry.cn-hangzhou.aliyuncs.com/javacspring/java8:1.0
VOLUME ../tmp
ADD miaosha-docker-1.0.0-SNAPSHOT.jar ./app.jar

ADD startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh

WORKDIR "/opt"
EXPOSE 5005 8080
ENTRYPOINT ["/opt/startup.sh"]

RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

