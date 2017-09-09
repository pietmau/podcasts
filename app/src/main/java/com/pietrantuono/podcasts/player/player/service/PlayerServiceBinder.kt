package com.pietrantuono.podcasts.player.player.service

import android.os.Binder
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.NotificatorService


class PlayerServiceBinder(val player: Player) : Binder(), Player by player, NotificatorService by (player as NotificatorService)