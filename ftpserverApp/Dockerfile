FROM openjdk:8-jdk-alpine
MAINTAINER Tao Wei <twei@research.ait.ie>
RUN mkdir app && mkdir app/files && mkdir app/logs
VOLUME ["/tmp"]
CMD ["/start.sh"]
ADD wait-for wait-for
ADD start.sh start.sh
ADD app.jar app.jar

