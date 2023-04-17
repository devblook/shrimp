plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.triumphteam.dev/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.mongodb:mongodb-driver-sync:4.8.1")
    implementation("team.unnamed:inject:1.0.1")
    implementation("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation("io.papermc:paperlib:1.0.8")
    implementation("net.kyori:adventure-text-minimessage:4.13.1")
}

tasks {
    shadowJar {
        archiveBaseName.set("Shrimp")
        archiveVersion.set("${project.version}")
        archiveClassifier.set("BETA")

        // Relocations
        relocate("me.fixeddev", "${rootProject.group}.shrimp.libs.commandflow")
        relocate("team.unnamed.inject", "${rootProject.group}.shrimp.libs.inject")
        relocate("org.mongodb.mongodb-driver-sync", "${rootProject.group}.shrimp.libs.commandflow")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}
bukkit {
    main = "team.devblook.shrimp.Shrimp"
    name = "Shrimp"
    version = project.version.toString()
    apiVersion = "1.17"
    libraries = listOf("org.mongodb:mongodb-driver-sync:4.8.1")
    authors = listOf("Call4han")

}