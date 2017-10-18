package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.pietrantuono.podcasts.settings.PreferencesManager

class DirectoryProviderImpl(
        private val context: Context,
        private val preferencesManager: PreferencesManager) : DirectoryProvider {
    override val downloadDir: String
        get() = getDir()

    private fun getDir() = preferencesManager.downloadDir ?: context.getFilesDir().absolutePath

    private val gigaByte = 1000 * 1000 * 1000

    override fun thereIsEnoughSpace(fileSize: Long): Boolean = context.filesDir.freeSpace - gigaByte > fileSize


}