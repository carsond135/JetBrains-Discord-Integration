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

sealed class Data {
    open class None private constructor() : Data() {
        companion object : None()

        override fun toString(): String = "Data.None"
    }

    open class Idle (val idleTimestamp: Long) : Data() {
        override fun toString(): String {
            return "Data.Idle(idleTimestamp=$idleTimestamp)"
        }
    }

    open class Application(
        val applicationName: String,
        val applicationVersion: String,
        val applicationTimeOpened: Long,
        val applicationTimeActive: Long,
        val applicationSettings: ApplicationSettings
    ) : Data() {
        override fun toString(): String {
            return "Data.Application(applicationVersion='$applicationVersion', applicationTimeOpened=$applicationTimeOpened, applicationTimeActive=$applicationTimeActive, " +
                    "applicationSettings=$applicationSettings)"
        }
    }

    open class Project(
        application
    )
}
