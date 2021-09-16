FROM adoptopenjdk/openjdk11
EXPOSE 8080
EXPOSE 5440
COPY build/libs/mafia-wizard-parent-1.0.0.jar mafia-wizard.jar
ENTRYPOINT ["java","-jar","mafia-wizard.jar"]