package com.pietrantuono.podcasts.player.player.service.messenger

import android.os.IBinder
import android.os.Messenger
import com.pietrantuono.podcasts.player.player.service.PlayerService


class MessagerInService {
    private val messenger: Messenger
    val iBinder: IBinder?
        get() = messenger?.binder

    constructor(player: PlayerService) {
        messenger = Messenger(HandlerInService(player))
    }

}