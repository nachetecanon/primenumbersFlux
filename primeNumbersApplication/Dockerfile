############################################################
##### downloading installations needed...              #####
############################################################
FROM ubuntu:latest as downloading_phase

ENV JAVA_VERSION=10.0.2-oracle
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




