rootProject.name = "mafia-wizard"
pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.openapi.generator") version openApiVersion
        id( "org.jetbrains.kotlin.plugin.serialization") version kotlinVersion

    }
}
buildscript {
    val kotlinVersion: String by settings
    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
        maven("https://kotlin.bintray.com/ktor")
    }

    dependencies {

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}


