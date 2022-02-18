package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Theme
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.ThemeMap

abstract class AbstractThemeMap(private val themes: Map<String, Theme>, override val default: Theme) : ThemeMap, Map<String, Theme> by themes {
    override fun toString(): String {
        return "AbstractThemeMap(default=$default, themes=$themes)"
    }
}
