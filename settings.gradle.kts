rootProject.name = "JetBrains-Discord-Integration"

include("icons")
include("plugin")
include("uploader")
include("bot")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    val properties = java.util.Properties().apply {
        this.load(java.nio.file.Files.newBufferedReader(settingsDir.toPath().resolve("gradle.properties")))
    }

    val versionGitVersions: String by properties
    val versionIntelliJGradle: String by properties
    val versionShadow: String by properties
    val versionVersions: String by properties
    val versionKotlin: String by properties

    plugins {
        kotlin("jvm") version versionKotlin
        id("com.github.ben-manes.versions") version versionVersions
        id("org.jetbrains.intellij") version versionIntelliJGradle
        id("com.github.johnrengelman.shadow") version versionShadow
        id("com.palantir.git-version") version versionGitVersions
    }
}
