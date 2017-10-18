package com.pietrantuono.podcasts.settings

interface PreferencesManager {
    val downloadDir: String?
    val downloadOnMobileNetwork: Boolean

}