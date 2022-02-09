import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    fileIndices
}

dependencies {
    val versionCoroutines: String by project
    val versionCommonsIo: String by project
    val versionJackson: String by project

    compileOnly(kotlin(module = "stdlib"))

    compileOnly(platform(kotlinx("coroutines-bom", versionCoroutines)))
    compileOnly(kotlinx("coroutines-core"))

    implementation(group = "commons-io", name = "commons-io", version = versionCommonsIo)

    implementation(platform("com.fasterxml.jackson:jackson-bom:$versionJackson"))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-core")
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind")
    implementation(group = "com.fasterxml.jackson.dataformat", name = "jackson-dataformat-yaml")
}

val minimizedJar: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    extendsFrom(configurations["implementation"], configurations["runtimeOnly"])
}

tasks {
    val minimizedJar by registering(ShadowJar::class) {
        group = "build"

        archiveClassifier.set("minimized")

        from(sourceSets.main.map(SourceSet::getOutput))

        val iconPaths = arrayOf(
            Regex("""/?discord/applications/.*\.png"""),
            Regex("""/?discord/themes/.*\.png""")
        )

        transform(PngOptimizingTransformer(128, *iconPaths))
    }

    artifacts {
        @Suppress("UnstableApiUsage")
        add("minimizedJar", minimizedJar.flatMap { it.archiveFile }) {
            builtBy(minimizedJar)
        }
    }

    generateFileIndices {
        paths += "discord/applications"
        paths += "discord/languages"
        paths += "discord/themes"
    }

    val generateIcons = create("generate-icons") {
        group = "icons"
    }

    val generateMaterialApplicationIcons = create<Exec>("generate-material-application-icons") {
        workingDir(project.file("src/main/resources/discord/applications/material"))
        commandLine = listOf(
            "magick",
            "mogrify",
            "-resize",
            "800x800",
            "-gravity",
            "center",
            "-bordercolor",
            "\"#23272A\"",
            "-border",
            "112x112",
            "-path",
            ".",
            "../*.png"
        )
    }

    val deleteMaterialApplicationIcons = create<Delete>("delete-material-application-icons") {
        delete(fileTree("src/main/resources/discord/applications/material/") {
            include("*.png")
        })
    }

    generateIcons.dependsOn(generateMaterialApplicationIcons)
    generateMaterialApplicationIcons.dependsOn(deleteMaterialApplicationIcons)
}
