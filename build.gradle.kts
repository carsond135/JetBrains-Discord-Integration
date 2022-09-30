@file:Suppress("SuspiciousCollectionReassignment")

import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files

plugins {
    kotlin("jvm") apply false
    id("com.github.ben-manes.versions")
    id("com.palantir.git-version")
}

group = "com.almightyalpaca.jetbrains.plugins.discord"

val versionDetails: Closure<VersionDetails> by project.extra

var version = versionDetails().lastTag.removePrefix("v")
version += when (versionDetails().commitDistance) {
    0 -> ""
    else -> "+${versionDetails().commitDistance}"
}

project.version = version

allprojects {
    repositories {
        mavenCentral()
    }

    fun secret(name: String) {
        project.extra[name] = System.getenv(name) ?: return
    }

    secret("DISCORD_TOKEN")
    secret("JETBRAINS_TOKEN")
}

subprojects {
    group = rootProject.group.toString() + "." + project.name.toLowerCase()
    version = rootProject.version

    val secrets: File = rootProject.file("secrets.gradle.kts")
    if (secrets.exists()) {
        apply(from = secrets)
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs += "-Xjvm-default=enable"

                languageVersion = "1.4"
            }
        }

        withType<JavaCompile> {
            targetCompatibility = "11"
            sourceCompatibility = "11"

            if (JavaVersion.current() >= JavaVersion.VERSION_1_9) {
                options.compilerArgs as MutableList<String> += listOf("--release", "11")
            }
        }
    }
}

defaultTasks = mutableListOf("default")

tasks {
    dependencyUpdates {
        gradleReleaseChannel = GradleReleaseChannel.CURRENT.toString()

        rejectVersionIf {
            val preview = Regex("""[+_.-]?(alpha|beta|rc|cr|m|preview|eap|pr|M)[.\d-_]*$""", RegexOption.IGNORE_CASE)
                .containsMatchIn(candidate.version)

            val wrongKotlinVersion = candidate.group.startsWith("org.jetbrains.kotlin") && candidate.version != currentVersion

            return@rejectVersionIf preview || wrongKotlinVersion
        }
    }

    withType<Wrapper> {
        val versionGradle: String by project

        distributionType = Wrapper.DistributionType.BIN
        gradleVersion = versionGradle
    }

    create<Delete>("clean") {
        group = "build"

        val regex = Regex("""JetBrains-Discord-Integration-\d+.\d+.\d+(?:\+\d+)?.zip""")

        Files.newDirectoryStream(project.projectDir.toPath())
            .filter { p -> regex.matches(p.fileName.toString()) }
            .forEach { p -> delete(p) }

        delete(project.buildDir)
    }

    create("default") {
        val buildPlugin = project.tasks.getByPath("plugin:buildPlugin") as Zip

        dependsOn(buildPlugin)

        doLast {
            copy {
                from(buildPlugin.outputs)
                into(".")
            }
        }
    }

    create<Delete>("clean-sandbox") {
        group = "build"

        delete(project.file(".sandbox"))
    }
}
