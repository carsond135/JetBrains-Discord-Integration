plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("docker")
}

docker {
    "bot" {
        tag = "almightyalpaca/jetbrains-discord-integration-bot"
        devContainerName = "${project.group}.dev"
        buildContainerName = "${project.group}.builder"
    }
}

application {
    mainClass("com.almightyalpaca.jetbrains.plugins.discord.bot.MainKt")
}

repositories {
    mavenCentral()
    maven(url = "https://m2.dv8tion.net/releases")
}

dependencies {
    val versionJda: String by project
    val versionOkHttp: String by project
    val versionKonf: String by project
    val versionLogback: String by project

    implementation(platform(kotlin(module = "bom")))

    implementation(kotlin(module = "stdlib"))

    implementation(kotlin(module = "script-util"))
    implementation(kotlin(module = "compiler-embeddable"))
    implementation(kotlin(module = "scripting-compiler-embeddable"))
    implementation(kotlin(module = "script-runtime"))

    implementation(group = "net.dv8tion", name = "JDA", version = versionJda) {
        exclude(group = "club.minnced", module = "opus-java")
    }

    implementation(group = "com.uchuhimo", name = "konf", version = versionKonf) {
        exclude(group = "com.moandjiezana.toml", module = "toml4j")
        exclude(group = "org.dom4j", module = "dom4j")
        exclude(group = "org.eclipse.jgit", module = "org.eclipse.jgit")
    }

    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = versionOkHttp)

    implementation(group = "ch.qos.logback", name = "logback-classic", version = versionLogback)
}
