#!/bin/bash
rm *.class
javac -classpath ".:lib/*" $1.java
java -classpath ".:lib/*" $1
