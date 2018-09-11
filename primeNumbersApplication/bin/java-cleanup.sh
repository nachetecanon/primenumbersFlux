#!/usr/bin/env bash

rm -rf  ${HOME}/.sdkman/candidates/java/current/*src.zip \
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
        ${HOME}/.sdkman/*.sh 2> /dev/null
