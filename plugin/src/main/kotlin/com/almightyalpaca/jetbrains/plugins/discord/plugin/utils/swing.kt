package com.almightyalpaca.jetbrains.plugins.discord.plugin.utils

import com.intellij.icons.AllIcons
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI
import java.awt.Color
import java.awt.GridBagConstraints
import javax.swing.*

inline fun gbc(block: (GridBagConstraints.() -> Unit)): GridBagConstraints = GridBagConstraints().apply(block)

fun createErrorMessage(message: String): JComponent = JPanel().apply warning@{
    this@warning.layout = BoxLayout(this@warning, BoxLayout.X_AXIS)

    background = Color(255, 25, 25, 150)

    add(JBLabel(AllIcons.General.ErrorDialog))
    add(Box.createHorizontalStrut(10))
    add(JBLabel("<html>$message</html>"))
    add(Box.createHorizontalGlue())

    border = JBUI.Borders.merge(JBUI.Borders.empty(10), JBUI.Borders.customLine(Color(255, 25, 25, 200)), true)
}

fun label(text: String, description: String? = null): JComponent = JBLabel(text).apply {
    border = JBUI.Borders.emptyRight(10)
    toolTipText = description
}

operator fun <T> DefaultComboBoxModel<T>.contains(o: Any?) = getIndexOf(o) != -1
