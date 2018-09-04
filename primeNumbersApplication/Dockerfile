############################################################
##### downloading installations needed...              #####
############################################################
FROM ubuntu:latest as downloading_phase

ENV SDKMAN_DIR="/root/.sdkman"

RUN mkdir -p  ${SDKMAN_DIR}/etc


COPY autoconfig ${SDKMAN_DIR}/etc/config

RUN set -ex && \
    apt update -y && \
    apt install bash curl zip unzip -y && \
    curl -sk "https://get.sdkman.io" | bash && \
    find ${SDKMAN_DIR} && \
    bash ${SDKMAN_DIR}/bin/sdkman-init.sh

