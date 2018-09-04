############################################################
##### downloading installations needed...              #####
############################################################
FROM ubuntu:latest as downloading_phase

ENV JAVA_VERSION=10.0.2-oracle

RUN set -ex && \
    apt update -y && \
    apt install bash ca-certificates tar bash curl unzip zip -y

RUN curl -sk "https://get.sdkman.io" | bash

COPY autoconfig /.sdkman/etc/config
COPY bin /usr/local/bin

RUN /bin/bash -c "chmod +x /usr/local/bin/sdkman-exec.sh && chmod +x /usr/local/bin/sdkman-wrapper.sh && chmod +x //root/.sdkman/bin/sdkman-init.sh"
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && /usr/local/bin/sdkman-wrapper.sh install java ${JAVA_VERSION}"



