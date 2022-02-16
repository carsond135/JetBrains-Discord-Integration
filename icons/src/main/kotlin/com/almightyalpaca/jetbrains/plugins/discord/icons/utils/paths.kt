package com.almightyalpaca.jetbrains.plugins.discord.icons.utils

import org.apache.commons.io.FilenameUtils
import java.nio.file.Path

inline val Path.name: String
    get() = FilenameUtils.getName(toString())

inline val Path.baseName: String
    get() = FilenameUtils.getBaseName(toString())

inline val Path.extension: String
    get() = FilenameUtils.getExtension(toString())
