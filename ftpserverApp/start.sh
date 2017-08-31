#!/bin/sh
#   Use this script to start Spring Boot container

if [ -n "$WAIT_FOR_HOST_AND_PORT" ]; then
    ./wait-for $WAIT_FOR_HOST_AND_PORT --
fi

appName="app.jar"
random="-Djava.security.egd=file:/dev/./urandom"

if [ -z "$SPRING_PROFILE" ]; then
    SPRING_PROFILE="container"
fi
profile="-Dspring.profiles.active=$SPRING_PROFILE"  

#DEBUG_PORT=8765
if [ -z "$DEBUG_PORT" ]; then
    debugOptions=""
else
	debugOptions="-agentlib:jdwp=transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=n"
fi  

exec java $random $profile $debugOptions -jar $appName

