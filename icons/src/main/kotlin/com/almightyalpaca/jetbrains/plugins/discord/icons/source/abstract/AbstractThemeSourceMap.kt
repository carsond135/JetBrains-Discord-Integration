package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.ThemeSource
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.ThemeSourceMap

abstract class AbstractThemeSourceMap(protected val map: Map<String, ThemeSource>) : ThemeSourceMap, Map<String, ThemeSource> by map
