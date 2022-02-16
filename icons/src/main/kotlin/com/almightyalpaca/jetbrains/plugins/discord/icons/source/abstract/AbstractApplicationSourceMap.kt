package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.ApplicationSource
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.ApplicationSourceMap

abstract class AbstractApplicationSourceMap(protected val map: Map<String, ApplicationSource>) : ApplicationSourceMap, Map<String, ApplicationSource> by map
