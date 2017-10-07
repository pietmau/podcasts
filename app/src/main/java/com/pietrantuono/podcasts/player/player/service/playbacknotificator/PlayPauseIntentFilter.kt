package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.content.IntentFilter


class PlayPauseIntentFilter : IntentFilter() {

    init {
        addAction(NotificationsConstants.ACTION_PAUSE)
        addAction(NotificationsConstants.ACTION_PLAY)

    }
}