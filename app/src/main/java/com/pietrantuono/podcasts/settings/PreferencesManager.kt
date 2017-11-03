package com.pietrantuono.podcasts.settings

interface PreferencesManager {
    val downloadOnMobileNetwork: Boolean
    val saveOnSdCard: Boolean
    val listenOnMobileNetwork: Boolean
}