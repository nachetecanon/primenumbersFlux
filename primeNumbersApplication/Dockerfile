############################################################
##### downloading installations needed...              #####
############################################################
FROM ubuntu:latest as downloading_phase

ARG JAVA_VERSION=10.0.2-oracle
ENV HOME=/home/predictx

RUN set -ex && \
    apt update -y && \
    apt install bash ca-certificates tar bash curl unzip zip -y && \
    useradd -m -s /bin/bash predictx && \
    mkdir -p ${HOME}/bin


RUN  curl -sk "https://get.sdkman.io" | bash

COPY --chown=predictx autoconfig ${HOME}/.sdkman/etc/config
COPY --chown=predictx bin/* ${HOME}/bin/

RUN  chmod +x ${HOME}/.sdkman/bin/sdkman-init.sh && \
     chmod +x ${HOME}/bin/sdkman-wrapper.sh && \
     ${HOME}/bin/sdkman-wrapper.sh install java ${JAVA_VERSION} && \
     find / -name "*.gz" -exec rm -fR {} \; 2> /dev/null && \
     find / -name "*.zip" -exec rm -fR {} \; 2> /dev/null && \
     find / -name "*.tar" -exec rm -fR {} \; 2> /dev/null

USER predictx

############################################################
##### installation of final artifact with alpine       #####
############################################################
FROM alpine:latest

ARG JAVA_VERSION=10.0.2-oracle
ENV HOME=/home/predictx
ENV JAVA_HOME=${HOME}/.sdkman/candidates/java/current
ENV PATH=${PATH}:${JAVA_HOME}/bin

ADD build/distributions/primeNumbersApplication-boot-4.10.tar /tmp

COPY --from=downloading_phase ${HOME}/ tmp/

RUN set -ex && \
    apk add --update --no-cache ca-certificates tar bash curl && \
    adduser -D -s /bin/bash predictx && \
    mkdir -p /opt /home/predictx/bin && \
    chown -R predictx:predictx /tmp /opt /home/predictx && \
    mv -f /tmp/bin/* /home/predictx/bin && \
    mv -f /tmp/primeNumbersApplication-boot-4.10 /home/predictx && \
    mv -f /tmp/.sdkman  /home/predictx && \
    rm -rf /var/cache/apk/* /tmp/*.apk

USER predictx






