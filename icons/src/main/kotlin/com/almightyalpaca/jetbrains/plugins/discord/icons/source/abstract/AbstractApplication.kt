package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Application

abstract class AbstractApplication(
    override val id: String,
    override val dummyFile: String
) : Application {
    override fun toString(): String {
        return "AbstractApplication(id='$id', dummyFile='$dummyFile')"
    }
}
