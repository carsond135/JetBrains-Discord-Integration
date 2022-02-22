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

@file:Suppress("DEPRECATION")

package com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.options.impl

import com.almightyalpaca.jetbrains.plugins.discord.plugin.utils.gbc
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.Box
import javax.swing.JComponent
import javax.swing.JPanel

@Suppress("DEPRECATION")
open class OptionHolderImpl : HiddenOptionHolderImpl() {
    override val component: JComponent? by lazy {
        JPanel().apply panel@{
            layout = GridBagLayout()

            var y = 0
            for (option in options.values) {
                add(option.component ?: continue, gbc {
                    gridx = 0
                    gridy = y++
                    gridwidth = 1
                    gridheight = 1
                    anchor = GridBagConstraints.NORTHWEST
                    fill = GridBagConstraints.HORIZONTAL
                    weightx = 1.0
                })
            }

            add(Box.createVerticalGlue(), gbc {
                gridx = 1
                gridy = y++
                gridwidth = 1
                gridheight = 1
                anchor = GridBagConstraints.NORTHWEST
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                weighty = 1.0
            })
        }
    }
}
