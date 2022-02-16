package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Application
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.ApplicationMap

abstract class AbstractApplicationMap(private val applications: Map<String, Application>) : ApplicationMap, Map<String, Application> by applications {
    override fun toString(): String {
        return "AbstractApplicationMap(applications=$applications)"
    }
}
