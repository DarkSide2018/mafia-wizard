plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

dependencies {
    val jacksonVersion: String by project


    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-junit"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

}

openApiGenerate {
    val openapiGroup = "${rootProject.group}.openapi"
    generatorName.set("kotlin") // Это и есть активный генератор
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("$rootDir/spec/game.yaml")


    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    configOptions.set(mapOf(
        "dateLibrary" to "string",
        "enumPropertyNaming" to "UPPERCASE",
        "serializationLibrary" to "jackson",
        "collectionType" to "list"
    ))
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}
