group = "mafia.wizard"
version = "1.0-SNAPSHOT"
plugins {
    kotlin("jvm") apply false
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}