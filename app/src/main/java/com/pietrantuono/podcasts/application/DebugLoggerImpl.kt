package com.pietrantuono.podcasts.application

import android.util.Log
import com.pietrantuono.podcasts.BuildConfig


class DebugLoggerImpl : DebugLogger {
    private val DEBUG: String = "debug"

    override fun debug(tag: String?, msg: String?) {
        if (shouldLog()) {
            Log.d(tag, msg)
        }
    }

    private fun shouldLog(): Boolean {
        return DEBUG.equals(BuildConfig.BUILD_TYPE, true)
    }
}
