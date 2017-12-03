package com.pietrantuono.podcasts.settings

import android.content.SharedPreferences

interface PreferencesManager {
    val downloadOnMobileNetwork: Boolean
    val saveOnSdCard: Boolean
    val listenOnMobileNetwork: Boolean
    fun registerListener(listener: (sharedPreferences: SharedPreferences, key: String) -> Unit)
    fun unRegisterListener(listener: (sharedPreferences: SharedPreferences, key: String) -> Unit)
    val downloadAutomatically: Boolean
}