package com.pietrantuono.podcasts.player.player.service

interface NotificatorService {
    var boundToFullScreen: Boolean?

    fun checkIfShouldNotify()
}