FROM adoptopenjdk/openjdk11
EXPOSE 8080
EXPOSE 5432
COPY mafia-wizard-master/build/libs/mafia-wizard-master-1.0.0.jar mafia-wizard.jar
ENTRYPOINT ["java","-XX:+UseContainerSupport","-Dspring.profiles.active=heroku","-jar","mafia-wizard.jar"]