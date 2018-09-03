############################################################
##### downloading installations needed...              #####
############################################################
FROM alpine:latest as downloading_phase

ADD build/distributions/deployment-tool.tar /opt/

ARG KOTLIN_VERSION="1.2.61"

RUN set -ex && \
    apk add --update --no-cache ca-certificates tar bash curl unzip zip && \
    adduser -D -s /bin/bash www-data



RUN curl -s "https://get.sdkman.io" > /tmp/sdk.sh && \
    chmod +x /tmp/sdk.sh && \
    source /tmp/sdk.sh
USER www-data


###############################################################
########    INSTALL KOTLIN    #################################
###############################################################

FROM alpine:latest

RUN set -ex && \
    apk add --update --no-cache ca-certificates bash curl unzip zip && \
    adduser -D -s /bin/bash www-data


COPY --from=downloading_phase /home/www-data/.sdkman /home/www-data/.sdkman

RUN chown -R www-data:www-data /home/www-data/.sdkman
USER www-data



