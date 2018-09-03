############################################################
##### downloading installations needed...              #####
############################################################
FROM ubuntu:latest as downloading_phase

ENV SDKMAN_DIR=/root/.sdkman

ARG KOTLIN_VERSION='1.2.61'

RUN set -ex && \
    apt update -y && \
    apt install curl zip unzip -y && \
    curl -s "https://get.sdkman.io" | bash && \
    echo "sdkman_auto_answer=true" > $SDKMAN_DIR/etc/config && \
    echo "sdkman_auto_selfupdate=false" >> $SDKMAN_DIR/etc/config && \
    echo "sdkman_insecure_ssl=true" >> $SDKMAN_DIR/etc/config && \
    ls -ltr ~/.sdkman/bin && \
    chmod +x ~/.sdkman/bin/*sh && \
    ~/.sdkman/bin/sdkman-init.sh


