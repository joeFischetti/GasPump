#! /bin/bash
# compile script to save a few keystrokes to compile at the command line

javac -cp lib/jssc.jar:lib/commons-net-3.6.jar src/*.java -d bin
