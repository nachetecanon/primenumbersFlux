######################################
#### SECOND IMAGE configure jdk    ###
######################################
FROM alpine:3.7


ENV LANG="C.UTF-8" \
    TZ="Europe/Paris" \
    JAVA_VERSION_MAJOR=8 \
    JAVA_VERSION_MINOR=181 \
    JAVA_VERSION_BUILD=13 \
    GLIBC_VERSION="2.23-r3" \
    JAVA_PACKAGE=jdk \
    JAVA_HOME=/opt/jdk \
    PATH="$PATH:/opt/jdk/bin" \
    JAVA_OPTS="-XshowSettings:vm -Djava.security.egd=file:///dev/./urandom -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=20 -XX:ConcGCThreads=5 -XX:InitiatingHeapOccupancyPercent=70 -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2" \
    SECURITY_COOKIE=96a7b8442fe848ef90c96a2fad6ed6d1


ARG MONITOR="NONE"


#git installed from alpine edge repository --> we need >2.15.0 version

RUN set -ex && \
    apk add --update --no-cache ca-certificates tar bash curl && \
    apk add git --update-cache --repository http://dl-cdn.alpinelinux.org/alpine/edge/main && \
    for pkg in glibc-${GLIBC_VERSION} glibc-bin-${GLIBC_VERSION} glibc-i18n-${GLIBC_VERSION}; do \
        curl -sSL https://github.com/andyshinn/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}/${pkg}.apk -o /tmp/${pkg}.apk; \
    done && \
    apk add --no-cache --allow-untrusted /tmp/*.apk && \
    curl -jksSLH "Cookie: oraclelicense=accept-securebackup-cookie" -o /tmp/java.tar.gz \
          http://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION_MAJOR}u${JAVA_VERSION_MINOR}-b${JAVA_VERSION_BUILD}/${SECURITY_COOKIE}/${JAVA_PACKAGE}-${JAVA_VERSION_MAJOR}u${JAVA_VERSION_MINOR}-linux-x64.tar.gz && \
    mkdir -p /opt/jdk1.${JAVA_VERSION_MAJOR}.0_${JAVA_VERSION_MINOR} /opt/repo && \
    ls -ltr /opt && \
    ls -ltr /opt/app && \
    tar -xf /tmp/java.tar.gz --strip-components=1 -C /opt/jdk1.${JAVA_VERSION_MAJOR}.0_${JAVA_VERSION_MINOR} && \
    ln -s /opt/jdk1.${JAVA_VERSION_MAJOR}.0_${JAVA_VERSION_MINOR} /opt/jdk && \
    rm -rf /opt/jdk/*src.zip \
           /opt/jdk/lib/missioncontrol \
           /opt/jdk/lib/visualvm \
           /opt/jdk/lib/*javafx* \
           /opt/jdk/jre/plugin \
           /opt/jdk/jre/bin/javaws \
           /opt/jdk/jre/bin/jjs \
           /opt/jdk/jre/bin/orbd \
           /opt/jdk/jre/bin/pack200 \
           /opt/jdk/jre/bin/policytool \
           /opt/jdk/jre/bin/rmid \
           /opt/jdk/jre/bin/rmiregistry \
           /opt/jdk/jre/bin/servertool \
           /opt/jdk/jre/bin/tnameserv \
           /opt/jdk/jre/bin/unpack200 \
           /opt/jdk/jre/lib/javaws.jar \
           /opt/jdk/jre/lib/deploy* \
           /opt/jdk/jre/lib/desktop \
           /opt/jdk/jre/lib/*javafx* \
           /opt/jdk/jre/lib/*jfx* \
           /opt/jdk/jre/lib/amd64/libdecora_sse.so \
           /opt/jdk/jre/lib/amd64/libprism_*.so \
           /opt/jdk/jre/lib/amd64/libfxplugins.so \
           /opt/jdk/jre/lib/amd64/libglass.so \
           /opt/jdk/jre/lib/amd64/libgstreamer-lite.so \
           /opt/jdk/jre/lib/amd64/libjavafx*.so \
           /opt/jdk/jre/lib/amd64/libjfx*.so \
           /opt/jdk/jre/lib/ext/jfxrt.jar \
           /opt/jdk/jre/lib/ext/nashorn.jar \
           /opt/jdk/jre/lib/oblique-fonts \
           /opt/jdk/jre/lib/plugin.jar \
           /var/cache/apk/*  \
           /tmp/*.apk && \
           mv /opt/jmxremote.password /opt/jdk/jre/lib/management && \
           chmod 600 /opt/jdk/jre/lib/management/jmxremote.password && \
           chmod 755 /opt/*.sh && \
           adduser -D -s /bin/bash www-data && \
           chown -R www-data:www-data /opt && \
           chown -R root:root /opt/jdk* && \
           chown -R www-data:www-data opt/jdk/jre/lib/management/jmxremote.password && \
           find / -name "*.gz" -exec rm -f {} \; 2> /dev/null


# Set JAVA and maven environment
ENV JAVA_HOME /opt/jdk
ENV PATH ${PATH}:${JAVA_HOME}/bin
ENV JMX_PORT 9010

USER www-data

# Maven settings

HEALTHCHECK --retries=3 \
    CMD [[ $(ls -ltr /opt/app/*.jar 2> /dev/null |wc -l) -ge 1 ]] || exit 1

CMD ["/opt/run.sh" ]