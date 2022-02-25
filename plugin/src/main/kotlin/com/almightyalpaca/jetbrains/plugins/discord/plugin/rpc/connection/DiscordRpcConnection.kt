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

package com.almightyalpaca.jetbrains.plugins.discord.plugin.rpc.connection

import club.minnced.discord.rpc.DiscordEventHandlers
import club.minnced.discord.rpc.DiscordRPC
import com.almightyalpaca.jetbrains.plugins.discord.plugin.DiscordPlugin
import com.almightyalpaca.jetbrains.plugins.discord.plugin.utils.DisposableCoroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.atomic.AtomicReference

class DiscordRpcConnection(
    override val appId: Long,
    private val userCallback: UserCallback
) : DiscordEventHandlers(), DiscordConnection, DisposableCoroutineScope {

    companion object {
        private var CONNECTED: AtomicReference<DiscordRpcConnection?> = AtomicReference(null)
        private val mutex = Mutex()
    }

    override val parentJob: Job = SupervisorJob()

    private var updateJob: Job? = null

    private lateinit var callbackRunner: ScheduledExecutorService

    init {
        ready = OnReady { user ->
            DiscordPlugin.LOG.info("Rpc connected, user: ${user.username}#${user.discriminator}")

            running = true
            userCallback(user.toGeneric())
        }
        disconnected = OnStatus { _, _ ->
            DiscordPlugin.LOG.info("Rpc disconnected")

            running = false
            userCallback(null)
        }
    }

    override val running: Boolean = false
        get() = field && CONNECTED.get() == this

    override suspend fun connect(): Unit = mutex.withLock {
        DiscordPlugin.LOG.debug("Starting new rpc connection")

        if (DiscordRPC.INSTANCE == null) {
            DiscordPlugin.LOG.error("DiscordRPC library isn't loaded")
            throw IllegalStateException("DiscordRPC isn't loaded")
        }

        if (!CONNECTED.)
    }
}
