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

package com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.impl

import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.OptionCreator
import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.OptionHolder
import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.OptionProvider
import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.types.Option
import kotlin.reflect.KProperty

class OptionProviderImpl<S, T : Option<S>>(private val options: OptionCreator<in S>, private val option: T) : OptionProvider<S> {
    override fun provideDelegate(thisRef: OptionHolder, prop: KProperty<*>): T {
        val name = prop.name

        options[name] = option

        return option
    }
}
