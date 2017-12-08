#!/bin/bash

./gradlew war
sudo cp build/libs/servlets.war /opt/wildfly-11.0.0.Final/standalone/deployments/
