############################################################
##### downloading installations needed...              #####
############################################################
FROM bitnami/minideb:stretch as downloading_phase

ARG DIST_FILE
ARG JAVA_VERSION=10.0.2-open
ENV HOME=/home/predictx
ENV LANG=C.UTF-8

RUN set -ex && \
    apt update -y && \
    apt install bash curl tar unzip zip  -y && \
    mkdir -p ${HOME}/bin && \
    useradd -d /home/predictx -s /bin/bash predictx


RUN  curl -s "https://get.sdkman.io" | bash

COPY --chown=predictx:predictx autoconfig ${HOME}/.sdkman/etc/config
COPY --chown=predictx:predictx bin/* ${HOME}/bin/
ADD --chown=predictx:predictx build/distributions/${DIST_FILE} ${HOME}/

RUN  chmod +x ${HOME}/.sdkman/bin/sdkman-init.sh && \
     chmod +x ${HOME}/bin/sdkman-wrapper.sh && \
     chown -R predictx:predictx ${HOME} && \
     ${HOME}/bin/sdkman-wrapper.sh install java ${JAVA_VERSION}


RUN  rm -rf ${HOME}/.sdkman/var \
            ${HOME}/.sdkman/ext \
            ${HOME}/.sdkman/bin \
            ${HOME}/.sdkman/src \
            ${HOME}/.sdkman/tmp \
            ${HOME}/.sdkman/etc \
            ${HOME}/.sdkman/archives \
            ${HOME}/.sdkman/candidates/java/current/*src.zip \
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
            ${HOME}/.sdkman/candidates/java/current/lib/plugin.ja 2> /dev/null && \
            find / -name "*.gz" -exec rm -f {} \; 2> /dev/null && \
            find / -name "*.zip" -exec rm -f {} \; 2> /dev/null && \
            find / -name "*.tar" -exec rm -f {} \; 2> /dev/null && \
            rm -fR /tmp/* 2> /dev/null


############################################################
##### leave only what's needed ...                     #####
############################################################
FROM bitnami/minideb:stretch

ARG DIST_FILE2
ARG DIST_VERSION
ARG JAVA_VERSION=10.0.2-open

ENV DIST_FILE2=$DIST_FILE2
ENV DIST_VERSION=$DIST_VERSION

ENV HOME=/home/predictx \
    LANG=C.UTF-8 \
    JAVA_HOME=/opt/jdk/${JAVA_VERSION} \
    PATH=${PATH}:/opt/jdk/${JAVA_VERSION}/bin

RUN useradd -d /home/predictx -s /bin/bash predictx

COPY --from=downloading_phase --chown=predictx:predictx /home/predictx/.sdkman/candidates/java/ /opt/jdk/
COPY --from=downloading_phase --chown=predictx:predictx /home/predictx/${DIST_FILE2}-boot-${DIST_VERSION} /home/predictx/

USER predictx


ENTRYPOINT  /home/predictx/bin/${DIST_FILE2}





