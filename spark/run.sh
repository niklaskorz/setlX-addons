#!/bin/sh

set -e
gradle fatJar
cp build/libs/setlX-spark.jar /usr/local/Cellar/setlx/2.6.0/
setlx demo.stlx
