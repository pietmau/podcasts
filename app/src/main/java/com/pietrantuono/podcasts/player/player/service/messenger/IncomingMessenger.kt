package com.pietrantuono.podcasts.player.player.service.messenger

import android.os.IBinder
import android.os.Messenger
import com.pietrantuono.podcasts.player.player.service.Player


class IncomingMessenger {
    private val messenger: Messenger
    val iBinder: IBinder?
        get() = messenger?.binder

    constructor(player: Player) {
        messenger = Messenger(IncomingHandler(player))
    }

}