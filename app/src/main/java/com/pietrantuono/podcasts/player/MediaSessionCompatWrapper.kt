package com.pietrantuono.podcasts.player

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.player.player.MediaSessionCompatCallbackImpl


class MediaSessionCompatWrapper(context: Context) {
    private val mediaSession: MediaSessionCompat

    init {
        mediaSession = MediaSessionCompat(context, MediaSessionCompatWrapper::class.java.simpleName)
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        val stateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
        mediaSession.setPlaybackState(stateBuilder!!.build())
        mediaSession.setCallback(MediaSessionCompatCallbackImpl(context))
    }

    val sessionToken: MediaSessionCompat.Token?
        get() = mediaSession.sessionToken
}