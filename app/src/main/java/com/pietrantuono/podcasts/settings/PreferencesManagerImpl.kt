package com.pietrantuono.podcasts.settings

import android.content.Context

class PreferencesManagerImpl(private val context: Context) : PreferencesManager {
    override val downloadDir: String
        get() = context.getFilesDir().absolutePath

}