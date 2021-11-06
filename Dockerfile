FROM adoptopenjdk/openjdk11
EXPOSE 8080
EXPOSE 5450
COPY build/libs/mafia-wizard-parent-1.0.0.jar mafia-wizard.jar
ENTRYPOINT ["java","-Dspring.profiles.active=heroku","-jar","mafia-wizard.jar"]