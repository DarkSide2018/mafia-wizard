FROM adoptopenjdk/openjdk11
EXPOSE 8080
EXPOSE 5432
COPY mafia-wizard-master/build/libs/mafia-wizard-master-1.0.0.jar mafia-wizard.jar
COPY mafia-wizard-master/src/main/resources mafia-wizard-master/src/main/resources
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:+UnlockExperimentalVMOptions","-XX:+UseZGC","-XX:MaxMetaspaceSize=256m","-Xms128m", "-Xmx128m", "-Xss512k","-Dspring.profiles.active=heroku","-jar","mafia-wizard.jar"]