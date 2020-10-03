#!/bin/bash

jar cvf pcj.jar -C target/classes lib/ -C target/cppbuild libPersistent.so

mvn install:install-file -Dfile=pcj.jar -DgroupId=com.intel -DartifactId=pcj -Dversion=1.0 -Dpackaging=jar
