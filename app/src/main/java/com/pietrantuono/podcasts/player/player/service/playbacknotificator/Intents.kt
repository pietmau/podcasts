package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.PendingIntent
import android.content.Intent


class Intents {
    val mPauseIntent: PendingIntent
    val mPlayIntent: PendingIntent
    val mPreviousIntent: PendingIntent
    val mNextIntent: PendingIntent
    val mStopCastIntent: PendingIntent

    init {
        mPauseIntent = PendingIntent.getBroadcast(service, Notificator.REQUEST_CODE,
                Intent(Notificator.ACTION_PAUSE).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPlayIntent = PendingIntent.getBroadcast(service, Notificator.REQUEST_CODE,
                Intent(Notificator.ACTION_PLAY).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPreviousIntent = PendingIntent.getBroadcast(service, Notificator.REQUEST_CODE,
                Intent(Notificator.ACTION_PREV).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mNextIntent = PendingIntent.getBroadcast(service, Notificator.REQUEST_CODE,
                Intent(Notificator.ACTION_NEXT).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mStopCastIntent = PendingIntent.getBroadcast(service, Notificator.REQUEST_CODE,
                Intent(Notificator.ACTION_STOP_CASTING).setPackage(pkg),
                PendingIntent.FLAG_CANCEL_CURRENT)
    }
}