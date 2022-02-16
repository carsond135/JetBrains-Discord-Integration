package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.IconSet
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Theme

abstract class AbstractIconSet(
    override val theme: Theme,
    override val applicationId: Long?,
    private val icons: Set<String>,
    override val applicationName: String
) : IconSet, Set<String> by icons {
    override fun toString(): String {
        return "AbstractIconSet(theme=$theme, applicationId=$applicationId, icons=$icons, applicationName='$applicationName')"
    }
}
