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

}

dependencies {

  compileOnly(libs.paper)

  compileOnly(libs.hikari)
  compileOnly(libs.mongo)

  compileOnly(libs.inject)
  implementation(libs.gui)
  compileOnly(libs.command)

  compileOnly(libs.lombok)
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
  }

  java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
  }

  paper {
    main = "team.devblook.shrimp.ShrimpPlugin"
    loader = "team.devblook.shrimp.ShrimpPluginLoader"
    name = "Shrimp"
    version = "${rootProject.version}"
    apiVersion = "1.21"
    authors = listOf("Jonakls")
  }
}

