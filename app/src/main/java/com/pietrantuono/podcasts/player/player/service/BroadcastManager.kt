package com.pietrantuono.podcasts.player.player.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.IntentsManager

class BroadcastManager(private val intentManager: IntentsManager) {

    fun registerForBroadcastsFromNotification(context: Context, receiver: BroadcastReceiver) {
        context.registerReceiver(receiver, getFilter())
    }

    private fun getFilter(): IntentFilter {
        val filter = IntentFilter()
        filter.addAction(intentManager.ACTION_NEXT)
        filter.addAction(intentManager.ACTION_PAUSE)
        filter.addAction(intentManager.ACTION_PLAY)
        filter.addAction(intentManager.ACTION_NEXT)
        return filter
    }

    fun unregisterForBroadcastsFromNotification(context: Context, receiver: BroadcastReceiver) {
        context.unregisterReceiver(receiver)
    }

    fun onReceive(intent: Intent?, player: Player) {
        intent?.action?.let {
            when (it) {
                intentManager.ACTION_PAUSE -> player.pause()
                intentManager.ACTION_PLAY -> player.play()
            }
        }

    }

}