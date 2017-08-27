package com.pietrantuono.podcasts.player.player.service.messenger

import android.os.Handler
import android.os.Message
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.player.player.service.PlayerService


internal class HandlerInService : Handler {
    private val player: Player
    private val notificatorService: NotificatorService

    constructor(playerService: PlayerService) {
        player = playerService
        notificatorService = playerService
    }

    companion object {
        val CHECK_SHOULD_NOTIFY: Int = 1
        val SET_EPISODE: Int = 2
    }

    override fun handleMessage(msg: Message?) {
        if (msg == null) {
            return
        }
        when (msg.what) {
            CHECK_SHOULD_NOTIFY -> checkShouldNotify()
            SET_EPISODE -> setEpisode(msg)
        }
    }

    private fun setEpisode(msg: Message) {
        (msg.obj as? Episode)?.let {
            player.setEpisode(it)
        }
    }

    private fun checkShouldNotify() {
        notificatorService.checkIfShouldNotify()
    }
}