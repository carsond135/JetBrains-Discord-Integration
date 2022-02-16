package com.almightyalpaca.jetbrains.plugins.discord.icons.source

import com.almightyalpaca.jetbrains.plugins.discord.icons.utils.stream
import com.almightyalpaca.jetbrains.plugins.discord.icons.utils.toMap

interface ApplicationSourceMap : Map<String, ApplicationSource> {
    fun createApplicationMap(applications: Map<String, Application>): ApplicationMap
    fun createApplication(id: String, dummyFile: String): Application

    fun toApplicationMap(): ApplicationMap {
        val applications = stream()
            .map { (key, value) -> key to value.asApplication() }
            .toMap()

        return createApplicationMap(applications)
    }

    fun ApplicationSource.asApplication(): Application {
        val dummyFile: String = node["dummyFile"]?.textValue()!!

        return createApplication(id, dummyFile)
    }
}

