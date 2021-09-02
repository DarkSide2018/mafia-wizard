plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    id ("org.flywaydb.flyway") version "7.14.1"
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
   implementation("org.postgresql:postgresql:$postgresDriverVersion")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.hibernate:hibernate-core:5.4.2.Final")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.1.51")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5440/wizard"
    user = "migration"
    password = "123456"
    baselineOnMigrate = true
    locations = arrayOf("filesystem:src/main/resources/db")
}

