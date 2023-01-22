plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://repo.papermc.io/repository/maven-public/")

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation("com.github.cryptomorin:XSeries:9.2.0") {
        isTransitive = false
    }
    implementation("org.mongodb:mongodb-driver-sync:4.8.1")
    implementation("team.unnamed:inject:1.0.1")
    implementation("dev.triumphteam:triumph-gui:3.1.2")
    implementation("me.fixeddev:commandflow-bukkit:0.5.2")
}

tasks {
    shadowJar {
        archiveBaseName.set("Shrimp")
        archiveVersion.set("${project.version}")
        archiveClassifier.set("")

        // Relocations
        relocate("dev.triumphteam.gui", "${rootProject.group}.shrimp.libs.gui")
        relocate("me.fixeddev", "${rootProject.group}.shrimp.libs.commandflow")
        relocate("team.unnamed.inject", "${rootProject.group}.shrimp.libs.inject")
        relocate("org.mongodb.mongodb-driver-sync", "${rootProject.group}.shrimp.libs.commandflow")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}