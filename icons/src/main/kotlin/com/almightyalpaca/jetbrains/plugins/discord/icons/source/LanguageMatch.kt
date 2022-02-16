package com.almightyalpaca.jetbrains.plugins.discord.icons.source

interface LanguageMatch {
    val name: String
    val assetIds: Iterable<String>

    fun findIcon(icons: IconSet): Icon?
}
