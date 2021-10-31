#!/usr/bin/env bash
echo "=========================="
echo "Creating postgres"
echo "----------------"

docker container stop mafia-wizard

docker container rm mafia-wizard

docker run --name mafia-wizard      \
             -e POSTGRES_USER=migration     \
             -e POSTGRES_PASSWORD=123456    \
             -e POSTGRES_DB=wizard -p 5440:5432 \
             -d postgres

./gradlew clean
./gradlew flywayMigrate
./gradlew openApiGenerate
./gradlew bootJar
echo "=========================="
echo "local env was created"
echo "----------------"