plugins {
  id("java")
  alias(libs.plugins.shadow)
  alias(libs.plugins.paperPlugin)
}

repositories {
  mavenCentral()
  mavenLocal()
  maven("https://repo.unnamed.team/repository/unnamed-public/")
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://repo.triumphteam.dev/snapshots/")

}

dependencies {

  compileOnly(libs.paper)

  implementation(libs.hikari)
  implementation(libs.mongo)

  implementation(libs.inject)
  implementation(libs.gui)
  implementation(libs.command)

  implementation(libs.lombok)
  annotationProcessor(libs.lombok)

  testCompileOnly(libs.inject)
  testAnnotationProcessor(libs.lombok)

  testImplementation(libs.jupiterApi)
  testRuntimeOnly(libs.jupiterEngine)
}

tasks {
  shadowJar {
    archiveBaseName.set("Shrimp")
    archiveVersion.set("${project.version}")
    archiveClassifier.set("BETA")

    // Relocations
    relocate("dev.triumphteam", "${rootProject.group}.shrimp.libs.command")
    relocate("team.unnamed", "${rootProject.group}.shrimp.libs.inject")
    relocate("org.mongodb", "${rootProject.group}.shrimp.libs.mongodb")
    relocate("com.zaxxer", "${rootProject.group}.shrimp.libs.hikari")
    relocate("org.projectlombok", "${rootProject.group}.shrimp.libs.bukkit")
  }

  java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
  }

  paper {
    main = "team.devblook.shrimp.ShrimpPlugin"
    name = "Shrimp"
    version = "1.0.0"
    apiVersion = "1.21"
    authors = listOf("Jonakls")
  }
}

