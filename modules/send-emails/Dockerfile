FROM alpine/java:21-jdk

ARG VERSION=0.0.0
ARG ARTIFACT_NAME=send-emails.jar
ENV TZ=America/Argentina/Buenos_Aires

LABEL maintainer="joalvarez <alvarez.joaquinez@gmail.com>" \
      org.label-schema.version=$VERSION \
      org.label-schema.schema-version="1.0"

WORKDIR /usr/src/app

COPY build/libs/${ARTIFACT_NAME} ${ARTIFACT_NAME}

EXPOSE 8090

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","send-emails.jar"]
