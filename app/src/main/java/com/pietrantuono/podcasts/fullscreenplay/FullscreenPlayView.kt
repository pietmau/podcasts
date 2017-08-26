package com.pietrantuono.podcasts.fullscreenplay

interface FullscreenPlayView {
    fun startTransitionPostponed()
    var title: String?
    fun loadImage(url: String?)
}