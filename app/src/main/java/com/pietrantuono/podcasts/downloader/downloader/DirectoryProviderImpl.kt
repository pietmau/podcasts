package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context

class DirectoryProviderImpl(private val context: Context) : DirectoryProvider {
    override val downloadDir: String
        get() = context.getFilesDir().absolutePath

    override fun thereIsEnoughSpace(fileSize: Long): Boolean =
            context.filesDir.freeSpace > fileSize


}