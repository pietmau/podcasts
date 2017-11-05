package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.os.Handler
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class SimpleExecutor(
        private val executor: ScheduledExecutorService,
        private val aHandler: Handler) {

    companion object {
        internal val PROGRESS_UPDATE_INTERNAL: Long = 1000
        internal val PROGRESS_UPDATE_INITIAL_INTERVAL: Long = 100
    }

    private var scheduleFuture: ScheduledFuture<*>? = null

    fun scheduleAtFixedRate(task: () -> Unit) {
        if (!executor.isShutdown) {
            scheduleFuture = executor.scheduleAtFixedRate({ aHandler.post(task) }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS)
        }
    }

    fun shutdown() {
        executor.shutdown()
    }

    fun cancel(mayInterrupt: Boolean) {
        scheduleFuture?.cancel(mayInterrupt)
    }
}