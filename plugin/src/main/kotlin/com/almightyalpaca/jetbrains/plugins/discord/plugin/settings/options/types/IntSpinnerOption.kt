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

package com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.types

import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.OptionCreator
import com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.impl.OptionProviderImpl

fun OptionCreator<in Int>.spinner(text: String, description: String? = null, initialValue: int, minValue: Int = Int.MIN_VALUE, maxValue: Int = Int.MAX_VALUE, step: Int = 1, format: String = "#",
                                  enabled: Boolean = true) =
    OptionProviderImpl(this, IntSpinnerOption(text, description, initialValue, step, minValue, maxValue, format, enabled))

fun OptionCreator<in Int>.spinner(text: String, initialValue: Int, minValue: Int = Int.MIN_VALUE, maxValue: Int = Int.MAX_VALUE, step: Int = 1, format: String = "#", enabled: Boolean = true) =
    OptionProviderImpl(this, IntSpinnerOption(text, null, initialValue, step, minValue, maxValue, format, enabled))

fun OptionCreator<in Int>.spinner(text: String, description: String? = null, initialValue: Int, range: IntRange, step: Int = 1, format: String = "#", enabled: Boolean = true) =
    spinner(text, description, initialValue, range.first, range.last, step, format, enabled)

fun OptionCreator<in Int>.spinner(text: String, initialValue: Int, range: IntRange, step: Int = 1, format: String = "#", enabled: Boolean = true) =
    spinner(text, null, initialValue, range.first, range.last, step, format, enabled)

class IntSpinnerOption(
    text: String,
    description: String?,
    initialValue: Int,
    private val step: Int,
    private val minValue: Int = Int.MIN_VALUE,
    private val maxValue: Int = Int.MAX_VALUE,
    private val format: String,
    private val enabled: Boolean
) : SimpleOption<Int>{
}
