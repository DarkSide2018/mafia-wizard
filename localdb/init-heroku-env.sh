#!/usr/bin/env bash
echo "=========================="
echo "Creating postgres"
echo "----------------"

docker container stop mafia-wizard-heroku

docker container rm mafia-wizard-heroku

docker run --name mafia-wizard-heroku      \
              --net=host \
             -e POSTGRES_USER=migration     \
             -e POSTGRES_PASSWORD=123456    \
             -e POSTGRES_DB=wizard -p 5450:5432 \
             -d postgres

./gradlew clean
./gradlew openApiGenerate
./gradlew bootJar
echo "=========================="
echo "local env was created"
echo "----------------"