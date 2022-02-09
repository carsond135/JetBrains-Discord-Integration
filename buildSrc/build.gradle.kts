import java.nio.file.Files
import java.util.*

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

var properties = Properties().apply {
    this.load(Files.newBufferedReader(rootDir.toPath().resolve("../gradle.properties")))
}

dependencies {
    val versionCommonsIo: String by properties
    val versionPngtastic: String by properties
    val versionShadow: String by properties
    val versionZeroAllocationHashing: String by properties

    implementation(group = "gradle.plugin.com.github.jengelman.gradle.plugins", name = "shadow", version = versionShadow)

    implementation(group = "com.github.depsypher", name = "pngtastic", version = versionPngtastic)
    implementation(group = "net.openhft", name = "zero-allocation-hashing", version = versionZeroAllocationHashing)

    implementation(group = "commons-io", name = "commons-io", version = versionCommonsIo)

    implementation(group = "com.github.docker-java", name = "docker-java", version = "3.2.1")
    implementation(group = "com.google.guava", name = "guava", version = "29.0-jre")
}

gradlePlugin {
    plugins {
        create("DockerPlugin") {
            id = "docker"
            implementationClass = "DockerPlugin"
        }

        create("FileIndicesPlugin") {
            id = "fileIndices"
            implementationClass = "FileIndicesPlugin"
        }
    }
}
