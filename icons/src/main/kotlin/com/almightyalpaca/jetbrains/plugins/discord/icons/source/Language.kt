package com.almightyalpaca.jetbrains.plugins.discord.icons.source

import com.almightyalpaca.jetbrains.plugins.discord.icons.matcher.Matcher

interface Language {
    val id: String
    val name: String
    val parent: Language?
    val matchers: Map<Matcher.Target, Matcher>
    val match: LanguageMatch
    val assetIds: Iterable<String>

    fun findMatch(target: Matcher.Target, fields: Collection<String>): LanguageMatch? {
        val matcher = matchers[target]
        if (matcher != null)
            if (fields.any { f -> matcher.matches(f) })
                return match

        return null
    }

    interface Simple : Language

    interface Default : Language {
        val assetId: String
    }
}
