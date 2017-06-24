package com.pietrantuono.podcasts.player.player

import android.net.Uri
import android.os.Parcelable

interface PodcastFeedSource : Parcelable {

    val uris: List<Uri>
}