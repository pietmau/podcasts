package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat


class SimpleMediaControllerCompatCallback(private val presenter: CustomControlsPresenter) : MediaControllerCompat.Callback() {

    override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
        presenter.updatePlaybackState(state)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        presenter.onMetadataChanged(metadata)
    }

}