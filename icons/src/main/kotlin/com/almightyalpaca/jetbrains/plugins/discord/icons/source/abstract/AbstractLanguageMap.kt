package com.almightyalpaca.jetbrains.plugins.discord.icons.source.abstract

import com.almightyalpaca.jetbrains.plugins.discord.icons.matcher.Matcher
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.Language
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.LanguageMap
import com.almightyalpaca.jetbrains.plugins.discord.icons.source.LanguageMatch

abstract class AbstractLanguageMap(private val languages: Collection<Language>) : LanguageMap, Collection<Language> by languages {
    override val default: Language.Default = find { l -> l.id == "default" } as Language.Default

    override fun findLanguage(provider: Matcher.Target.Provider): LanguageMatch {
        for (target in Matcher.Target.values()) {
            val fields = provider.getField(target)
            for (language in this) {
                val match = language.findMatch(target, fields)
                if (match != null) {
                    return match
                }
            }
        }

        return default.match
    }

    override fun toString(): String {
        return "AbstractLanguageMap(default=$default, languages=$languages)"
    }
}
