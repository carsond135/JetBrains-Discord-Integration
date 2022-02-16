package com.almightyalpaca.jetbrains.plugins.discord.icons.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Suppress("EXPERIMENTAL_API_USAGE")
fun <T> Deferred<T>.getCompletedOrNull() = when (isCompleted) {
    true -> getCompleted()
    false -> null
}

fun <T> CoroutineScope.retryAsync(
    policy: RetryPolicy = RetryPolicy.Exponential(),
    initial: Boolean = false,
    log: (Exception) -> Unit = {},
    block: suspend () -> T
) = async {
    retry(policy, initial, log, block)
}

suspend fun <T> retry(
    policy: RetryPolicy = RetryPolicy.Exponential(),
    initial: Boolean = false,
    log: (Exception) -> Unit = {},
    block: suspend () -> T
): T {
    try {
        if (!initial) {
            return block()
        }
    } catch (e: Exception) {
        log(e)
    }

    policy.wait()

    return retry(policy.next, false, log, block)
}

sealed class RetryPolicy(value: Long, val maxValue: Long) {
    val value = value.coerceAtMost(maxValue)

    abstract val next: RetryPolicy

    suspend fun wait(): RetryPolicy {
        delay(value * 1000)

        return next
    }

    class Exponential(value: Long = 2, maxValue: Long = 60, private val factor: Int = 2) : RetryPolicy(value, maxValue) {
        override val next: Exponential
            get() = Exponential(value * factor, maxValue, factor)
    }
}
