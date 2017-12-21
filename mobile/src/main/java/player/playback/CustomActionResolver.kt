package player.playback


import android.os.Bundle

class CustomActionResolver {

    companion object {
        const val EXTRA_EPISODE_URI = "episode_uri"
        const val CUSTOM_ACTION_ADD_TO_QUEUE = "custom_action_add_to_queue"
        const val CUSTOM_ACTION_DOWNLOAD_AND_ADD_TO_QUEUE = "custom_action_download_and_add_to_queue"
        const val CUSTOM_ACTION_THUMBS_UP = "com.example.android.uamp.THUMBS_UP"
    }

    fun onCustomAction(action: String, extras: Bundle, playbackManager: PlaybackManager) {
        if (CUSTOM_ACTION_ADD_TO_QUEUE == action) {
            getUri(extras)?.let { playbackManager.onNewItemEnqueued(it) }
        }
        if (CUSTOM_ACTION_DOWNLOAD_AND_ADD_TO_QUEUE == action) {
            getUri(extras)?.let { playbackManager.downloadAndAddToQueue(it) }
        }
    }

    private fun getUri(extras: Bundle) = extras?.getString(EXTRA_EPISODE_URI)
}
