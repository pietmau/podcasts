package com.pietrantuono.podcasts.player

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import com.pietrantuono.podcasts.application.App
import java.util.*
import javax.inject.Inject

class MediaPlaybackService : MediaBrowserServiceCompat() {
    @Inject lateinit var mediaSession: MediaSessionCompatWrapper

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent.inject(this)
        sessionToken = mediaSession!!.sessionToken
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int,
                           rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot("", null)
    }

    override fun onLoadChildren(parentMediaId: String,
                                result: Result<List<MediaBrowserCompat.MediaItem>>) {
        val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
        result.sendResult(mediaItems)
    }

}

