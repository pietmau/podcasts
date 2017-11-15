package com.pietrantuono.podcasts.main.presenter

import com.pietrantuono.podcasts.BuildConfig

class BuildTypeCheckerImpl : BuildTypeChecker {
    private val DEBUG: String = "debug"

    override fun isDebug(): Boolean = DEBUG.equals(BuildConfig.BUILD_TYPE, true)
}