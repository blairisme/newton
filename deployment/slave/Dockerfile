FROM rappdw/docker-java-python


RUN mkdir -p /.newton_slave/

ADD ./libs/engine-0.1.0.jar engine-0.1.0.jar
EXPOSE 8080

RUN sh -c 'touch /engine-0.1.0.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/engine-0.1.0.jar"]