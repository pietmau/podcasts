package com.pietrantuono.podcasts.fullscreenplay

interface FullscreenPlayView {
    fun setImage(imageUrl: String)
    fun startTransitionPostponed()
    var title: String?
}