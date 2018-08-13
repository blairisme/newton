# Newton (c)
#
# This work is licensed under the MIT License. To view a copy of this
# license, visit
#
#      https://opensource.org/licenses/MIT
#
# Author: Ziad Halabi
#

FROM rappdw/docker-java-python:latest

LABEL version="1.0"
LABEL description="Contains the Newton slave application"
LABEL maintainer="ziad.halabi.17@ucl.ac.uk"
MAINTAINER Ziad Halabi <ziad.halabi.17@ucl.ac.uk>

ARG master_host
ARG master_port
ARG application_host
ARG application_port

ENV newton_master_host $master_host
ENV newton_master_port $master_port
ENV newton_application_host $application_host
ENV newton_application_port $application_port

RUN mkdir -p /.newton_slave/


RUN apt-get update && apt-get install -y python python-dev python-pip \
    libxft-dev libfreetype6 libfreetype6-dev

# Install matplotlib
RUN pip install 'matplotlib==1.4.3'


COPY ./libs/engine.jar engine.jar
EXPOSE 8080

RUN sh -c 'touch /engine.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/engine.jar"]
