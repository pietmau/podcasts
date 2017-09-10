package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.BuildConfig
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity

class IntentsManager(private val context: Context) {

    val REQUEST_CODE = 1
    val PACKAGE = BuildConfig.APPLICATION_ID
    val ACTION_PREV = PACKAGE + ".previous"
    val ACTION_PAUSE = PACKAGE + ".pause"
    val ACTION_PLAY = PACKAGE + ".play"
    val ACTION_NEXT = PACKAGE + ".next"
    val ACTION_STOP = PACKAGE + ".stop"

    val playIntent: PendingIntent
        get() = PendingIntent.getBroadcast(context, REQUEST_CODE,
                Intent(ACTION_PLAY).setPackage(PACKAGE), PendingIntent.FLAG_CANCEL_CURRENT)

    val stopIntent: PendingIntent
        get() = PendingIntent.getBroadcast(context, REQUEST_CODE,
                Intent(ACTION_STOP).setPackage(PACKAGE), PendingIntent.FLAG_CANCEL_CURRENT)

    val previousIntent: PendingIntent
        get() = PendingIntent.getBroadcast(context, REQUEST_CODE,
                Intent(ACTION_PREV).setPackage(PACKAGE), PendingIntent.FLAG_CANCEL_CURRENT)

    val pauseIntent: PendingIntent
        get() = PendingIntent.getBroadcast(context, REQUEST_CODE,
                Intent(ACTION_PAUSE).setPackage(PACKAGE), PendingIntent.FLAG_CANCEL_CURRENT)

    val nextIntent: PendingIntent
        get() = PendingIntent.getBroadcast(context, REQUEST_CODE,
                Intent(ACTION_NEXT).setPackage(PACKAGE), PendingIntent.FLAG_CANCEL_CURRENT)

    fun createContentIntent(context: Context, description: Any?): PendingIntent? {
        val openUI = Intent(context, FullscreenPlayActivity::class.java)
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        return PendingIntent.getActivity(context, REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT)
    }
}