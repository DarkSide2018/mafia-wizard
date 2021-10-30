# mafia-wizard

command for create docker - image

./gradlew bootBuildImage --imageName=springio/gs-spring-boot-docker

create postgres

sh scripts/init-postgres.sh

command for migrate postgres

gradle flywayMigrate  -PdbUrl=jdbc:postgresql://localhost:5432/wizard -PdbPassword=123456 -PdbUser=migration


https://stackoverflow.com/questions/59953765/display-an-image-file-from-spring-boot-backend-in-javascript-front-end

display foto from postgres