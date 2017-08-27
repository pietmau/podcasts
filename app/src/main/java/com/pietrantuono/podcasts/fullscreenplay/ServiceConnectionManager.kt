package com.pietrantuono.podcasts.fullscreenplay

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.PlayerService
import com.pietrantuono.podcasts.player.player.service.messenger.MessengerInUi


class ServiceConnectionManager {
    private var notificatorService: NotificatorService? = null

    private var connection: ServiceConnection? = object : ServiceConnection {
        override fun onServiceDisconnected(componentName: ComponentName?) {
            notificatorService = null
        }

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            notificatorService = MessengerInUi(iBinder)
            notificatorService?.boundToFullScreen = true
            notificatorService?.checkIfShouldNotify()
        }
    }

    fun bind(activity: Activity) {
        val serviceIntent = Intent(activity, PlayerService::class.java)
        activity.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbind(activity: Activity) {
        notificatorService?.boundToFullScreen = false
        notificatorService?.checkIfShouldNotify()
        activity.unbindService(connection)
    }
}