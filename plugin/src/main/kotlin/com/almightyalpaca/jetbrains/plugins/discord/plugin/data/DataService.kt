/*
 * Copyright 2017-2020 Aljoscha Grebe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.almightyalpaca.jetbrains.plugins.discord.plugin.data

import com.almightyalpaca.jetbrains.plugins.discord.plugin.DiscordPlugin
import com.almightyalpaca.jetbrains.plugins.discord.plugin.render.Renderer
import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.settings
import com.almightyalpaca.jetbrains.plugins.discord.plugin.time.timeActive
import com.almightyalpaca.jetbrains.plugins.discord.plugin.utils.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service

val dataService: DataService
    get() = service()

@Service
class DataService {
    suspend fun getData(mode: Renderer.Mode): Data? = tryOrNull { mode.run { getData() } }

    @JvmName("getDataInternal")
    private suspend fun (Renderer.Mode).getData(): Data {
        DiscordPlugin.LOG.debug("Getting data")

        val applicationSettings = settings

        val application = ApplicationManager.getApplication()
        val applicationInfo = ApplicationInfoEx.getInstance()
        val applicationName = settings.applicationType.getValue().applicationNameReadable
        val applicationVersion = applicationInfo.fullVersion
        val applicationTimeOpened = application.timeOpened
        val applicationTimeActive = application.timeActive
    }
}
