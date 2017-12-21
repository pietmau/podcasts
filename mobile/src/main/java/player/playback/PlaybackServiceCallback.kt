package player.playback


import android.support.v4.media.session.PlaybackStateCompat

interface PlaybackServiceCallback {
    fun onPlaybackStart()

    fun onNotificationRequired()

    fun onPlaybackStop()

    fun onPlaybackStateUpdated(newState: PlaybackStateCompat)

    fun downloadAndAddToQueue(uri: String)
}
