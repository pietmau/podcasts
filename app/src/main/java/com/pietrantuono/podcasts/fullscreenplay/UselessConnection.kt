package com.pietrantuono.podcasts.fullscreenplay

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder


object UselessConnection : ServiceConnection {
    override fun onServiceDisconnected(componentName: ComponentName?) {

    }

    override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {

    }
}