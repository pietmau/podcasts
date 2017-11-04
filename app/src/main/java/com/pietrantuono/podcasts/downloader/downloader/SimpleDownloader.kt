package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.downloader.service.DownloaderService


abstract class SimpleDownloader(private val context: Context) :Downloader {

    internal fun startService(intent: Intent) {
        context.startService(intent)
    }

    internal fun getIntent(command: String): Intent {
        val intent = Intent(context, DownloaderService::class.java)
        intent.putExtra(DownloaderService.EXTRA_COMMAND, command)
        return intent
    }

}