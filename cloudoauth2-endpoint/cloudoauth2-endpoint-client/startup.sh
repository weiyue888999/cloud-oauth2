#!/bin/sh
basedir=`cd $(dirname $0); pwd -P`
lib_dir=${basedir}"/lib"
CLASSPATH=
for i in ${lib_dir}/*.jar;do
    CLASSPATH=$CLASSPATH:"$i"
done
java -classpath $CLASSPATH com.zfsoft.cloud.authentication.endpoint.client.AuthenticationEndpointClientApplication