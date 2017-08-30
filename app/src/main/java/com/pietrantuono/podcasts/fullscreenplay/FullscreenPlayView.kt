package com.pietrantuono.podcasts.fullscreenplay

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.service.Player

interface FullscreenPlayView {
    fun startTransitionPostponed()
    var title: String?
    fun setEpisode(episode: Episode?)
}