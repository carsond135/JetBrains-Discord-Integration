package com.almightyalpaca.jetbrains.plugins.discord.plugin.utils

import com.intellij.openapi.application.ApplicationManager
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun ScheduledExecutorService.scheduleWithFixedDelay(delay: Long, initialDelay: Long = delay, unit: TimeUnit, action: () -> Unit): ScheduledFuture<*> =
    scheduleWithFixedDelay(Runnable(action), initialDelay, delay, unit)

suspend fun <T> invokeOnEventThread(action: () -> T): T {
    val app = ApplicationManager.getApplication()

    return invokeSuspendingOn(action, app::isDispatchThread, app::invokeLater)
}

suspend fun <T> invokeReadAction(action: () -> T): T {
    val app = ApplicationManager.getApplication()

    return invokeSuspendingOn(action, app::isReadAccessAllowed, app::runReadAction)
}

suspend fun <T> invokeWriteAction(action: () -> T): T {
    val app = ApplicationManager.getApplication()

    return invokeSuspendingOn(action, app::isWriteAccessAllowed, app::runWriteAction)
}

suspend fun <T> invokeSuspendingOn(action: () -> T, executorNotRequired: () -> Boolean, executor: (Runnable) -> Unit): T =
    if (executorNotRequired()) {
        action()
    } else {
        suspendCoroutine { continuation ->
            executor(Runnable { continuation.resumeWithAction(action) })
        }
    }

fun <T> Continuation<T>.resumeWithAction(action: () -> T) {
    try {
        val result = action()
        resume(result)
    } catch (e: Exception) {
        resumeWithException(e)
    }
}
