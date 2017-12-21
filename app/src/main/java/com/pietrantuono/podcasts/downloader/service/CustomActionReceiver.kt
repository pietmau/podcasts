package com.pietrantuono.podcasts.downloader.service


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.startService
import player.OtherActions

class CustomActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        getUri(intent)?.let { uri ->
            context?.startService<DownloaderService>(
                    EXTRA_COMMAND to COMMAND_DOWNLOAD_EPISODE,
                    EXTRA_TRACK to uri,
                    PLAY_WHEN_READY to true)
        }
    }

    private fun getUri(intent: Intent?): String? = intent?.getStringExtra(OtherActions.URI_EXTRA)

}
