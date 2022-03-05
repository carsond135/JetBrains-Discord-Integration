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

package com.almightyalpaca.jetbrains.plugins.discord.plugin.render

import com.almightyalpaca.jetbrains.plugins.discord.plugin.DiscordPlugin
import com.almightyalpaca.jetbrains.plugins.discord.plugin.utils.DisposableCoroutineScope
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.util.Disposer
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.apache.tools.ant.taskdefs.Execute.launch
import java.util.concurrent.ScheduledFuture

val renderService: RenderService
    get() = service()

@Service
class RenderService : DisposableCoroutineScope {
    override val parentJob: Job = SupervisorJob()

    private var renderClockJob: ScheduledFuture<*>? = null

    private var renderJob: Job? = null

    @Synchronized
    fun render(force: Boolean = false) {
        renderJob?.let {
            DiscordPlugin.LOG.debug("Canceling previous render due to new request")
            it.cancel()
        }

        renderJob = launch {
            DiscordPlugin.LOG.debug("Scheduling render, force=$force")

            if (Disposer.isDisposed(this@RenderService)) {
                DiscordPlugin.LOG.debug("Skipping render, service already disposed")
                return@launch
            }

            val data = dataService.getData(Renderer.Mode.NORMAL) ?: return@launch
        }
    }
}
