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

COPY --chown=predictx:predictx autoconfig ${HOME}/.sdkman/etc/config
COPY --chown=predictx:predictx bin/* ${HOME}/bin/
ADD  --chown=predictx:predictx build/distributions/primeNumbersApplication-boot-4.10.tar ${HOME}


RUN  chmod +x ${HOME}/.sdkman/bin/sdkman-init.sh && \
     chmod +x ${HOME}/bin/sdkman-wrapper.sh && \
     ${HOME}/bin/sdkman-wrapper.sh install java ${JAVA_VERSION} && \
     find / -name "*.gz" -exec rm -fR {} \; 2> /dev/null && \
     find / -name "*.zip" -exec rm -fR {} \; 2> /dev/null && \
     find / -name "*.tar" -exec rm -fR {} \; 2> /dev/null && \
     rm -rf ${HOME}/.sdkman/candidates/java/current/*src.zip \
                ${HOME}/.sdkman/candidates/java/current/lib/missioncontrol \
                ${HOME}/.sdkman/candidates/java/current/lib/visualvm \
                ${HOME}/.sdkman/candidates/java/current/lib/*javafx* \
                ${HOME}/.sdkman/candidates/java/current/lib/plugin \
                ${HOME}/.sdkman/candidates/java/current/bin/javaws \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/jjs \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/orbd \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/pack200 \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/policytool \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/rmid \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/rmiregistry \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/servertool \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/tnameserv \
                ${HOME}/.sdkman/candidates/java/current/lib/bin/unpack200 \
                ${HOME}/.sdkman/candidates/java/current/lib/javaws.jar \
                ${HOME}/.sdkman/candidates/java/current/lib/deploy* \
                ${HOME}/.sdkman/candidates/java/current/lib/desktop \
                ${HOME}/.sdkman/candidates/java/current/lib/*javafx* \
                ${HOME}/.sdkman/candidates/java/current/lib/*jfx* \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libdecora_sse.so \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libprism_*.so \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libfxplugins.so \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libglass.so \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libgstreamer-lite.so \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libjavafx*.so \
                ${HOME}/.sdkman/candidates/java/current/lib/amd64/libjfx*.so \
                ${HOME}/.sdkman/candidates/java/current/lib/ext/jfxrt.jar \
                ${HOME}/.sdkman/candidates/java/current/lib/ext/nashorn.jar \
                ${HOME}/.sdkman/candidates/java/current/lib/oblique-fonts \
                ${HOME}/.sdkman/candidates/java/current/lib/plugin.jar \
                ${HOME}/.sdkman/bin \
                ${HOME}/.sdkman/var \
                ${HOME}/.sdkman/tmp \
                ${HOME}/.sdkman/archives \
                /tmp/*.apk ${HOME}/.sdkman/*.sh

############################################################
##### installation of final artifact with alpine       #####
############################################################
FROM alpine:latest

ARG JAVA_VERSION=10.0.2-oracle
ENV HOME=/home/predictx \
    JAVA_HOME=/home/predictx/.sdkman/candidates/java/current \
    PATH=${PATH}:/home/predictx/.sdkman/candidates/java/current/bin \
    GLIBC_VERSION="2.28-r0"

COPY --from=downloading_phase ${HOME}/ /home/predictx/

RUN set -ex && \
    apk add --no-cache bash && \
    apk add --no-cache --allow-untrusted /tmp/*.apk && \
    adduser -D -s /bin/bash predictx && \
    mkdir -p /opt /home/predictx/bin && \
    chown -R predictx:predictx /tmp /opt /home/predictx && \
    find / -name "*.gz" -exec rm -fR {} \; 2> /dev/null && \
    find / -name "*.zip" -exec rm -fR {} \; 2> /dev/null && \
    find / -name "*.tar" -exec rm -fR {} \; 2> /dev/null && \
    rm -fr /var/cache/apk/* /tmp/*.apk ${HOME}/bin

USER predictx

#HEALTHCHECK
#ENTRYPOINT ["/"]




