#!/bin/bash
rm *.class
javac -classpath ".:+libs/*" $1.java
java -classpath ".:+libs/*" $1
