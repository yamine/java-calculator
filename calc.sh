#!/bin/bash

export CLASSPATH=./log4j-api-2.5.jar:./log4j-core-2.5.jar
echo $CLASSPATH
java -jar target/calculator.jar "$1" "$2" "$3"
