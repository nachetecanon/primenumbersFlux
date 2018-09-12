############################################################
##### downloading installations needed...              #####
############################################################
FROM bitnami/minideb:stretch as downloading_phase

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
            ${HOME}/.sdkman/archives

############################################################
##### leave only what's needed ...                     #####
############################################################
FROM bitnami/minideb:stretch

ARG JAVA_VERSION=10.0.2-open
ENV HOME=/home/predictx \
    LANG=C.UTF-8 \
    JAVA_HOME=/opt/jdk/${JAVA_VERSION} \
    PATH=${PATH}:/opt/jdk/${JAVA_VERSION}/bin




RUN set -ex && \
    useradd -d /home/predictx -s /bin/bash predictx  && \
    find / -name "*.gz" -exec rm -fR {} \; 2> /dev/null && \
    find / -name "*.zip" -exec rm -fR {} \; 2> /dev/null && \
    find / -name "*.tar" -exec rm -fR {} \; 2> /dev/null && \
    rm -fR /tmp/* 2> /dev/null

COPY --from=downloading_phase --chown=predictx:predictx /home/predictx/.sdkman/candidates/java/ /opt/jdk/
ADD  --chown=predictx:predictx build/distributions/primeNumbersApplication-boot-4.10.tar ${HOME}

USER predictx


ENTRYPOINT /home/predictx/primeNumbersApplication-boot-4.10/bin/primeNumbersApplication





