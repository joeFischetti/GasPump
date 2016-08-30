#! /bin/bash
#  script for running the app as root required for the serial port on my system

sudo java -cp lib/sqlite-jdbc-3.8.7.jar:lib/jssc.jar:bin GasPump
