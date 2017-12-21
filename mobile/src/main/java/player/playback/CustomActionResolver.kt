package player.playback


import android.os.Bundle
import player.playback.PlaybackManager.EXTRA_EPISODE_URI

class CustomActionResolver {

    companion object {
        const val CUSTOM_ACTION_ADD_TO_QUEUE = "custom_action_add_to_queue"
        const val CUSTOM_ACTION_DOWNLOAD_AND_ADD_TO_QUEUE = "custom_action_download_and_add_to_queue"
        const val CUSTOM_ACTION_THUMBS_UP = "com.example.android.uamp.THUMBS_UP"
    }

    fun onCustomAction(action: String, extras: Bundle, playbackManager: PlaybackManager) {
//        if (CUSTOM_ACTION_THUMBS_UP == action) {
//            LogHelper.i(TAG, "onCustomAction: favorite for current track")
//            val currentMusic = queueManager.getCurrentMusic()
//            if (currentMusic != null) {
//                val mediaId = currentMusic!!.getDescription().getMediaId()
//                if (mediaId != null) {
//                    val musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId!!)
//                    musicProvider.setFavorite(musicId, !musicProvider.isFavorite(musicId))
//                }
//            }
//            updatePlaybackState(null)
//            return
//        }

        if (CUSTOM_ACTION_ADD_TO_QUEUE == action) {
            if (extras == null || extras.getString(EXTRA_EPISODE_URI) == null) {
                return
            }
            val uri = extras.getString(EXTRA_EPISODE_URI)
            playbackManager.onNewItemEnqueued(uri)
        }
    }
}
