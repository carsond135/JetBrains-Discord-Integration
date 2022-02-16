package com.almightyalpaca.jetbrains.plugins.discord.icons.source

import com.almightyalpaca.jetbrains.plugins.discord.icons.utils.getCompletedOrNull
import kotlinx.coroutines.Deferred

interface Source {
    fun getLanguagesAsync(): Deferred<LanguageMap>
    fun getThemesAsync(): Deferred<ThemeMap>
    fun getApplicationAsync(): Deferred<ApplicationMap>

    suspend fun getLanguages(): LanguageMap = getLanguagesAsync().await()
    suspend fun getThemes(): ThemeMap = getThemesAsync().await()
    suspend fun getApplications(): ApplicationMap = getApplicationsAsync().await()

    fun getLanguagesOrNull(): LanguageMap? = getLanguagesAsync().getCompletedOrNull()
    fun getThemesOrNull(): ThemeMap? = getThemesAsync().getCompletedOrNull()
    fun getApplicationsOrNull(): ApplicationMap? = getApplicationsAsync().getCompletedOrNull()
}
