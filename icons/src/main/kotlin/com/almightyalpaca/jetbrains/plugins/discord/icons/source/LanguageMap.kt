package com.almightyalpaca.jetbrains.plugins.discord.icons.source

import com.almightyalpaca.jetbrains.plugins.discord.icons.matcher.Matcher

interface LanguageMap : Collection<Language> {
    val default: Language.Default
    fun findLanguage(provider: Matcher.Target.Provider): LanguageMatch
}
