package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.LanguageSource
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.LanguageSourceMap

abstract class AbstractLanguageSourceMap(protected val map: Map<String, LanguageSource>) : LanguageSourceMap, Map<String, LanguageSource> by map
