package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import android.os.Environment
import com.pietrantuono.podcasts.settings.PreferencesManager
import java.io.File


class DirectoryProviderImpl(
        private val context: Context,
        private val preferencesManager: PreferencesManager,
        private val SLACK_IN_BYTES: Int) : DirectoryProvider {
    
    override val externalStorageIsAvailable: Boolean
        get() = externalStorageIsAvailable()

    override val downloadDir: String
        get() = getDir()

    private fun getDir(): String {
        if (preferencesManager.saveOnSdCard && externalStorageIsAvailable()) {
            return getExternalStorageDir() ?: getInternalStorageDir()
        } else {
            return getInternalStorageDir()
        }
    }

    private fun getInternalStorageDir() = context.getFilesDir().absolutePath

    override fun thereIsEnoughSpace(fileSize: Long): Boolean = getUsableSpace() - SLACK_IN_BYTES > fileSize

    private fun getUsableSpace() = File(getDir()).usableSpace

    private fun externalStorageIsAvailable(): Boolean {
        val state = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED.equals(state)) {
            true
        } else false
    }

    fun getExternalStorageDir(): String? = context.getExternalFilesDir(null)?.absolutePath
}