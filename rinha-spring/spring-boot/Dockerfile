FROM eclipse-temurin:17-jdk-alpine        
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/people-manager-0.1.jar springboot.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar springboot.jar
