package com.almightyalpaca.jetbrains.plugins.discord.plugin.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> throwing(initializer: () -> Throwable): ReadWriteProperty<Any?, T> = ThrowingDelegate(initializer)

private class ThrowingDelegate<T>(private val initializer: () -> Throwable) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>) = throw initializer()
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = throw initializer()
}

fun <T> modifying(initialValue: T, modifier: (T) -> T): ReadWriteProperty<Any?, T> =
    ModifyingDelegate(initialValue, modifier)

private class ModifyingDelegate<T>(initialValue: T, val modifier: (T) -> T) : ReadWriteProperty<Any?, T> {
    var value = modifier(initialValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = modifier(value)
    }
}

fun <T> verifying(initialValue: T, predicate: (T) -> Boolean): ReadWriteProperty<Any?, T> =
    VerifyingProperty(initialValue, predicate)

private class VerifyingProperty<T>(initialValue: T, val predicate: (T) -> Boolean) : ReadWriteProperty<Any?, T> {
    var value: T = initialValue

    init {
        if (!predicate(value)) {
            throw IllegalArgumentException()
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (predicate(value)) {
            this.value = value
        } else {
            throw IllegalArgumentException()
        }
    }
}
