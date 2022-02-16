package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Asset
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Theme

abstract class AbstractAsset(override val id: String, override val theme: Theme) : Asset {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Asset) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
