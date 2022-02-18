package com.almightyalpaca.jetbrains.plugins.discord.plugin.utils

import com.almightyalpaca.jetbrains.plugins.discord.plugin.DiscordPlugin
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.serviceContainer.AlreadyDisposedException

inline fun <T> tryOrNull(print: Boolean = true, block: () -> T) = tryOrDefault(null, print, block)

inline fun <T> tryOrDefault(default: T, print: Boolean = true, block: () -> T): T {
    return try {
        block()
    } catch (e: ProcessCanceledException) {
        throw e
    } catch (e: Exception) {
        if (print && e::class.simpleName != "AlreadyDisposedException") {
            DiscordPlugin.LOG.error(e)
        }

        default
    }
}
