############################################################
##### downloading installations needed...              #####
############################################################
FROM alpine:latest as downloading_phase

ARG JAVA_VERSION=10.0.2-open
ENV HOME=/home/predictx
ENV LANG=C.UTF-8

RUN set -ex && \
    apk add --no-cache bash curl tar zip && \
    adduser -D -s /bin/bash predictx && \
    mkdir -p ${HOME}/bin


RUN  curl -sk "https://get.sdkman.io" | bash

COPY --chown=predictx:predictx autoconfig ${HOME}/.sdkman/etc/config
COPY --chown=predictx:predictx bin/* ${HOME}/bin/
ADD  --chown=predictx:predictx build/distributions/primeNumbersApplication-boot-4.10.tar ${HOME}


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
            ${HOME}/.sdkman/archives && \
            find / -name "*.gz" -exec rm -fR {} \; 2> /dev/null && \
            find / -name "*.zip" -exec rm -fR {} \; 2> /dev/null && \
            find / -name "*.tar" -exec rm -fR {} \; 2> /dev/null && \
            rm -fr /var/cache/apk/* /tmp/*.apk 2> /dev/null


FROM alpine:latest

ARG JAVA_VERSION=10.0.2-zulu
ENV HOME=/home/predictx \
    LANG=C.UTF-8 \
    JAVA_HOME=/home/predictx/.sdkman/candidates/java/current \
    PATH=${PATH}:/home/predictx/.sdkman/candidates/java/current/bin



RUN set -ex && \
    apk add --no-cache bash curl gcc musl-dev && \
    adduser -D -s /bin/bash predictx

COPY --from=downloading_phase --chown=predictx:predictx /home/predictx/ /home/predictx/


USER predictx



#HEALTHCHECK
ENTRYPOINT /home/predictx/primeNumbersApplication-boot-4.10/bin/primeNumbersApplication





