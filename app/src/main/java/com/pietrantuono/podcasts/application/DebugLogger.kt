package com.pietrantuono.podcasts.application

import android.util.Log
import com.pietrantuono.podcasts.BuildConfig
import javax.inject.Inject


class DebugLogger @Inject constructor() {
    private val DEBUG: String = "debug"

    fun debug(tag: String?, msg: String?) {
        if (shouldLog()) {
            Log.d(tag, msg)
        }
    }

    private fun shouldLog(): Boolean {
        return DEBUG.equals(BuildConfig.BUILD_TYPE, true)
    }
}
