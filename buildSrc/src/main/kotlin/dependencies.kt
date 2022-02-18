fun ktor(module: String, version: String? = null) = "io.ktor:ktor-$module:${version ?: ""}"

fun kotlinx(module: String, version: String? = null) = "org.jetbrains.kotlinx:kotlinx-$module:${version ?: ""}"
