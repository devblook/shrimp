plugins{
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")

}
dependencies{
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
    implementation("team.unnamed:inject:1.0.1")
}
java{
    toolchain{
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}