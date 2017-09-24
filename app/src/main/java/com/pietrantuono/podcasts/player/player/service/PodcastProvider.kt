package com.pietrantuono.podcasts.player.player.service


import android.support.v4.media.MediaMetadataCompat

interface PodcastProvider {
    fun getMusic(mediaId: String?): MediaMetadataCompat?
}
