FROM openjdk:11
MAINTAINER sam
COPY target/bot-0.0.1-SNAPSHOT.jar /opt/bot.jar
ENTRYPOINT ["java","-jar","/opt/bot.jar"]