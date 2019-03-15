#!/bin/sh
mvn clean package && docker build -t nl.han.oose.dea.dewihu/spotitube .
docker rm -f spotitube || true && docker run -d -p 8080:8080 -p 4848:4848 --name spotitube nl.han.oose.dea.dewihu/spotitube 
