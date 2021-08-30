plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm")
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

dependencies {
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.1.51")
    implementation("com.h2database:h2:1.4.200")
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
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "mafia.wizard.AppGeneralKt"
    }
    dependsOn(configurations.runtimeClasspath)
    from({
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
