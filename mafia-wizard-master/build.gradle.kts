plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm")
    id("org.flywaydb.flyway") version "7.14.1"
    kotlin("plugin.spring") version "1.5.21"
}
val springBootVersion: String by project
val springDependencyManagementVersion: String by project
group = "mafia.wizard"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11
repositories {
    mavenCentral()
}
val postgresDriverVersion: String by project

dependencies {
    val jacksonVersion: String by project
    implementation(project(":wizard-player-transport"))
    implementation(project(":user-game-transport"))
    implementation(project(":transport-objects"))
    implementation("org.imgscalr:imgscalr-lib:4.2")
    implementation("org.flywaydb:flyway-core")
    implementation("com.vladmihalcea:hibernate-types-55:2.12.1")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.hibernate:hibernate-core:5.5.7.Final")
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

