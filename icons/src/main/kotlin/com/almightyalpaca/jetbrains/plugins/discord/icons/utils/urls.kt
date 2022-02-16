package com.almightyalpaca.jetbrains.plugins.discord.icons.utils

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

fun <T> URL.get(handler: (InputStream) -> T): T? = try {
    val connection = (openConnection() as HttpURLConnection).apply {
        setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36")
    }

    connection.inputStream.use { inputStream ->
        handler(inputStream)
    }
} catch (e: Exception) {
    null
}
