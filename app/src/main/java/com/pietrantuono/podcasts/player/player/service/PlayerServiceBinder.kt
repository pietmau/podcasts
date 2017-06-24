package com.pietrantuono.podcasts.player.player.service

import android.os.Binder


class PlayerServiceBinder(val player: Player) : Binder(), Player {
}