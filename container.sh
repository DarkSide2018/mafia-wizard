#!/usr/bin/env bash
echo "=========================="
echo "start build container"
echo "----------------"

./gradlew clean
./gradlew build
./gradlew openApiGenerate
./gradlew bootJar

docker container stop mafia-back
docker container rm mafia-back
docker build -t mafia-back .
docker run mafia-back
echo "=========================="
echo "finish build container"
echo "----------------"