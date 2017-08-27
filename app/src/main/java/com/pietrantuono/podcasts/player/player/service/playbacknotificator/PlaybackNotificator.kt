package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import com.pietrantuono.podcasts.player.player.service.NotificatorService


interface PlaybackNotificator {
    fun shuldNotify(notificatorService: NotificatorService): Boolean
}