rootProject.name = "mafia-wizard-parent"
pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.21"
        kotlin("kapt") version "1.5.0"
        id("org.openapi.generator") version "5.2.0"
    }
}

include("mafia-wizard-master")
include("wizard-player-transport")
include("user-game-transport")
include("transport-objects")

