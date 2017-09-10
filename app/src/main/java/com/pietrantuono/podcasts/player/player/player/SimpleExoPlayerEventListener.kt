package com.pietrantuono.podcasts.player.player.player

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray


open class SimpleExoPlayerEventListener: ExoPlayer.EventListener {
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        Log.d("EventListener", "onPlaybackParametersChanged " + playbackParameters )
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        Log.d("EventListener", "onTracksChanged " + trackGroups + " " + trackSelections)
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        Log.d("EventListener", "onPlayerError " + error)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Log.d("EventListener", "onLoadingChanged " + isLoading)
    }

    override fun onPositionDiscontinuity() {

    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
        Log.d("EventListener", "onTimelineChanged " + timeline + " " + manifest)
    }
}