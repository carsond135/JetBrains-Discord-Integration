package com.almightyalpaca.jetbrains.plugins.discord.icons.source

interface IconSet : Set<String> {
    val theme: Theme
    val applicationId: Long?
    val applicationName: String

    fun getAsset(assetId: String): Asset?
}
