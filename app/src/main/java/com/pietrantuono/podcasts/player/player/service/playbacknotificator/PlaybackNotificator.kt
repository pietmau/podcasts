package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import com.pietrantuono.podcasts.player.player.service.NotificatorService


interface PlaybackNotificator {
    fun checkIfShoudBeForeground(notificatorService: NotificatorService): Boolean
}