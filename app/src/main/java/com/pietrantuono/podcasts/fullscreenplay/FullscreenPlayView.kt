package com.pietrantuono.podcasts.fullscreenplay

import com.pietrantuono.podcasts.apis.Episode

interface FullscreenPlayView {
    fun startTransitionPostponed()
    var title: String?
    fun setEpisode(episode: Episode?)
    fun enterWithoutTransition()
}