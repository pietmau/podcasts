package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.PlayerService

class ServiceConnector : ServiceConnection {
    private var notificatorService: NotificatorService? = null

    override fun onServiceDisconnected(name: ComponentName?) {
        serviceUnbound()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        onServiceBound(service)
    }

    private fun serviceUnbound() {
        notificatorService?.boundToFullScreen = false
    }

    private fun onServiceBound(iBinder: IBinder?) {
        notificatorService = iBinder as? NotificatorService
        notificatorService?.boundToFullScreen = true
    }

    fun unbindService(activity: Activity) {
        serviceUnbound()
        activity.unbindService(this)
    }

    fun bindService(activity: Activity) {
        activity.bindService(Intent(activity, PlayerService::class.java), this, Context.BIND_AUTO_CREATE)
    }
}