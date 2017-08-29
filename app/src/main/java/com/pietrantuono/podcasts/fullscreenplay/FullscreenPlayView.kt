package com.pietrantuono.podcasts.fullscreenplay

import com.pietrantuono.podcasts.apis.Episode

interface FullscreenPlayView {
    fun startTransitionPostponed()
    var title: String?
    fun loadImage(url: String?)
    fun setEpisode(episode: Episode)
}