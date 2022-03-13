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

package com.almightyalpaca.jetbrains.plugins.discord.plugin.settings.gui.preview

import com.almightyalpaca.jetbrains.plugins.discord.plugin.utils.*
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.image.BufferedImage

class PreviewRenderer {
    private val user = User()
    private val game = Game()

    private val width = 250
    private val height = 273

    private var image: BufferedImage = createImage(width, height)

    val dummy by lazy lazy@{
        val image = createImage(width, height)

        image.withGraphics {
            color = Color(0, 0, 0, 0)
            fillRect(0, 0, image.width, image.height)
        }

        return@lazy image
    }

    private val font16Bold: Font = Roboto.bold.deriveFont(16F)
    private val font16Regular: Font = Roboto.regular.deriveFont(16F)
    private val font14Bold: Font = Roboto.bold.deriveFont(14F)
    private val font14Medium: Font = Roboto.medium.deriveFont(13F)
    private val font11Black: Font = Roboto.black.deriveFont(11F)

    private val font16BoldMetrics: FontMetrics = image.graphics.getFontMetrics(font16Bold)
    private val font16RegularMetrics: FontMetrics = image.graphics.getFontMetrics(font16Regular)
    private val font14BoldMetrics: FontMetrics = image.graphics.getFontMetrics(font14Bold)
    private val font14MediumMetrics: FontMetrics = image.graphics.getFontMetrics(font14Medium)
    private val font11BlackMetrics: FontMetrics = image.graphics.getFontMetrics(font11Black)

    private val font11BlackHeight: Int = font11BlackMetrics.height

    private val font16BoldBaseline: Int = font16BoldMetrics.maxAscent + font16BoldMetrics.leading
    private val font16RegularBaseline: Int = font16RegularMetrics.maxAscent + font16RegularMetrics.leading
    private val font14BoldBaseline: Int = font14BoldMetrics.maxAscent + font14BoldMetrics.leading
    private val font14MediumBaseline: Int = font14MediumMetrics.maxAscent + font14MediumMetrics.leading
    private val font11BlackBaseline: Int = font11BlackMetrics.maxAscent + font11BlackMetrics.leading

    private val font14BoldMaxHeight: Int = font14BoldMetrics.maxAscent + font14BoldMetrics.leading + font14BoldMetrics.maxDescent
    private val font14MediumMaxHeight: Int = font14MediumMetrics.maxAscent + font14MediumMetrics.leading + font14MediumMetrics.maxDescent

    @Synchronized
    suspend fun draw(type: Renderer.Type.Application, force: Boolean = false): ModifiedImage {

    }
}
