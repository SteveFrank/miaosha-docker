#!/bin/bash
java -agentlib:jdwp=transport=dt_socket,address=5005,suspend=y,server=y -cp /opt/*:/opt Main