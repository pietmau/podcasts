package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.downloader.service.DownloaderService
import com.pietrantuono.podcasts.downloader.service.EXTRA_COMMAND
import hugo.weaving.DebugLog


abstract class SimpleDownloader(private val context: Context) :Downloader {

    @DebugLog
    internal fun startService(intent: Intent) {
        context.startService(intent)
    }

    internal fun getIntent(command: String): Intent {
        val intent = Intent(context, DownloaderService::class.java)
        intent.putExtra(EXTRA_COMMAND, command)
        return intent
    }

}