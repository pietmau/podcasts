package com.pietrantuono.podcasts.player.player.service.provider


import android.graphics.Bitmap
import android.support.v4.media.MediaMetadataCompat

interface PodcastProvider {
    fun getMusic(mediaId: String?): MediaMetadataCompat?
    fun updateMusicArt(musicId: String?, bitmap: Bitmap, icon: Bitmap)
}
