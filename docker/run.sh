#!/bin/bash

echo "######## BEGIN jar installation ##################";


function help() {
    echo $1;
    echo -e "Parameters: \t[GROUP_ID] -> mandatory, maven groupId of the maven artifact"
    echo -e "\t\t [ARTIFACT_ID] -> mandatory, artifactId of the maven artifact"
    echo -e "\t\t [VERSION] -> optional, LATEST if no provided"
    echo -e "\t\t [ARGS] -> optional, list of arguments for the JVM"
    echo -e "\t\t [DEBUG] -> optional, if true app will be launched with debug mode at port 5005"
    echo -e "\t\t [JMX] -> optional, if true app will be launched with jmx mode at port 9010"
    echo -e "\t\t [WAITFOR] -> optional, host and port to wait for before launching myself <host:port>"
    exit -1;
}

function logOtherParams() {
	echo "Other params passed to script... "
    echo "\t[ARGS] =\"${ARGS}\"";
    echo "\t[DEBUG] =\"${DEBUG}\"";
    echo "\t[WAITFOR] =\"${WAITFOR}\"";
    echo "==============================="
}

if [ -eq $1 == "--help" ]; then help "Help info";fi;

# remove source artifacts if launched from target dir in local installation
rm -f /opt/app/*sources.jar 2> /dev/null
rm -f /opt/app/*javadoc.jar 2> /dev/null

# check if jar available from local dir
embeddedjartolaunch=$(ls -ltr /opt/app/*.jar 2> /dev/null |wc -l);
if [ $embeddedjartolaunch -eq 1 ];
then
	SPRINGBOOT_JAR=$(ls /opt/app/*.jar | head)
	echo "Embedded jar $SPRINGBOOT_JAR, deploying it....";
else
    echo "No jar available to launch, so exit with error..."
    exit -3;
fi	

# log other parameters
logOtherParams;

if [ ! -z "$WAITFOR" ];then
	/opt/waitfor.sh $WAITFOR --child
fi

echo "######## END jar installation ##################";

# check for debug options
if [ -z "${DEBUG}" ];then
	echo "no debug option selected";
else
	echo "debug option detected";
	export JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"

fi;

echo "###### launching java process..... #####"
firstline=$(head -1 $SPRINGBOOT_JAR > /dev/null);
# touch our file so it has modification time
touch $SPRINGBOOT_JAR

EXE=$([[ ${firstline} =~  ^#!.*$ ]] && echo true);
echo "exe = ${EXE} "
if [ ! -z ${EXE} ];then
    echo "Self executable file";
else
    echo "Non executable file, just jar";
fi;

if [ ! -z "${JMX}" ];
then
   export JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.port=9010\
                -Dcom.sun.management.jmxremote.authenticate=false \
               -Dcom.sun.management.jmxremote.ssl=false \
               -Dcom.sun.management.jmxremote.rmi.port=9010 \
               -Djava.rmi.server.hostname=localhost ";
fi
if [ ! -z "${ARGS}" ];
then
    export JAVA_OPTS="${JAVA_OPTS} ${ARGS}"
fi
exec java $JAVA_OPTS -jar $SPRINGBOOT_JAR ;