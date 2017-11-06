FROM alpine:edge
RUN apk add --no-cache openjdk8
COPY ./UnlimitedJCEPolicyJDK8/* \
    /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/boxPlayground-0.1.0.jar /app/boxPlayground.jar
ADD config.json /app/config.json
ENTRYPOINT ["java", "-jar", "/app/boxPlayground.jar"]