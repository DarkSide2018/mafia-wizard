FROM adoptopenjdk/openjdk11
EXPOSE 8080
COPY build/libs/mafia-wizard-1.0.0.jar mafia-wizard.jar
ENTRYPOINT ["java","-jar","mafia-wizard.jar"]