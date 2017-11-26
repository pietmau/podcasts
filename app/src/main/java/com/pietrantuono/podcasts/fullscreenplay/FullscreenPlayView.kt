package com.pietrantuono.podcasts.fullscreenplay

import diocan.pojos.Episode


interface FullscreenPlayView {
    fun startTransitionPostponed()
    var title: String?
    fun setEpisode(episode: Episode?)
    fun enterWithoutTransition()
    fun onError(errorMessage: CharSequence?)
}