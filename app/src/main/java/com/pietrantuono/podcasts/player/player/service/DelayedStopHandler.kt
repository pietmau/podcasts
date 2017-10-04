package com.pietrantuono.podcasts.player.player.service

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

class DelayedStopHandler(service: MusicService) : Handler() {
    private val mWeakReference: WeakReference<MusicService>

    init {
        mWeakReference = WeakReference(service)
    }

    override fun handleMessage(msg: Message) {
        val service = mWeakReference.get()
        if (service != null && service.playbackManager.playback != null) {
            if (service.playbackManager.playback?.isPlaying == true) {
                return
            }
            service.stopSelf()
        }
    }
}