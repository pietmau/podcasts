package player.playback


import android.content.res.Resources
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.PlaybackStateCompat
import com.example.android.uamp.R
import player.model.MusicProvider
import player.utils.MediaIDHelper
import player.utils.WearHelper

class PlaybackStateUpdater(
        private val musicProvider: MusicProvider,
        private val resources: Resources,
        private val playback: Playback?,
        private val serviceCallback: PlaybackServiceCallback) {

    fun updatePlaybackState(error: String?, queueManager: QueueManager) {
        var position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN
        if (playback != null && playback.isConnected) {
            position = playback.currentStreamPosition
        }
        val stateBuilder = PlaybackStateCompat.Builder()
                .setActions(getAvailableActions(playback))
        setCustomAction(stateBuilder, queueManager, musicProvider, resources)
        var state = playback!!.state
        if (error != null) {
            stateBuilder.setErrorMessage(error)
            state = PlaybackStateCompat.STATE_ERROR
        }
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime())
        val currentMusic = queueManager.currentMusic
        if (currentMusic != null) {
            stateBuilder.setActiveQueueItemId(currentMusic.queueId)
        }
        serviceCallback.onPlaybackStateUpdated(stateBuilder.build())
        if (state == PlaybackStateCompat.STATE_PLAYING || state == PlaybackStateCompat.STATE_PAUSED) {
            serviceCallback.onNotificationRequired()
        }
        updateProgress(position, queueManager)
    }

    private fun updateProgress(position: Long, queueManager: QueueManager) {

    }

    private fun setCustomAction(stateBuilder: PlaybackStateCompat.Builder,
                                queueManager: QueueManager, musicProvider: MusicProvider,
                                resources: Resources) {
        val currentMusic = queueManager.currentMusic ?: return
        val mediaId = currentMusic.description.mediaId ?: return
        val musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId)
        val favoriteIcon = if (musicProvider.isFavorite(musicId)) R.drawable.ic_star_on else R.drawable.ic_star_off
        val customActionExtras = Bundle()
        WearHelper.setShowCustomActionOnWear(customActionExtras, true)
        stateBuilder.addCustomAction(PlaybackStateCompat.CustomAction.Builder(
                CustomActionResolver.CUSTOM_ACTION_THUMBS_UP, resources.getString(R.string.favorite), favoriteIcon)
                .setExtras(customActionExtras)
                .build())
    }

    private fun getAvailableActions(playback: Playback?): Long {
        var actions = PlaybackStateCompat.ACTION_PLAY_PAUSE or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT
        if (playback?.isPlaying == true) {
            actions = actions or PlaybackStateCompat.ACTION_PAUSE
        } else {
            actions = actions or PlaybackStateCompat.ACTION_PLAY
        }
        return actions
    }
}
