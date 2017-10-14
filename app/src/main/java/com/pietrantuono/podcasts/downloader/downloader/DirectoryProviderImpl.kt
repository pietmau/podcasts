package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context

class DirectoryProviderImpl(private val context: Context) : DirectoryProvider {
    override val downloadDir: String
        get() = context.getFilesDir().absolutePath

    private val gigaByte = 1000 * 1000 * 1000

    override fun thereIsEnoughSpace(fileSize: Long): Boolean = context.filesDir.freeSpace + gigaByte > fileSize


}