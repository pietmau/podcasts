package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.pietrantuono.podcasts.settings.PreferencesManager
import java.io.File

class DirectoryProviderImpl(
        private val context: Context,
        private val preferencesManager: PreferencesManager,
        private val SLACK_IN_BYTES: Int) : DirectoryProvider {
    override val downloadDir: String
        get() = getDir()

    private fun getDir() = preferencesManager.downloadDir ?: context.getFilesDir().absolutePath

    override fun thereIsEnoughSpace(fileSize: Long): Boolean = getUsableSpace() - SLACK_IN_BYTES > fileSize

    private fun getUsableSpace() = File(getDir()).usableSpace


}