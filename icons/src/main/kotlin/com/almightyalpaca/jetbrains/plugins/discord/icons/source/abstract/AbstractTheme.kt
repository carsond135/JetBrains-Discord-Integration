package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Theme

abstract class AbstractTheme(
    override val id: String,
    override val name: String,
    override val description: String,
    override val applications: Map<String, Long>
) : Theme {
    override fun toString(): String {
        return "AbstractTheme(id='$id', name='$name', description='$description', applications=$applications)"
    }
}
