package com.pietrantuono.podcasts.player.player.service

import android.app.Service


abstract class InstrumentedService : Service() {

    companion object {
        val TAG: String = "PlayerService"
    }
}