package com.pietrantuono.podcasts.fullscreenplay.model


import diocan.pojos.Episode
import rx.Observer


interface FullscreenModel {
    var episode: Episode?
    fun getEpisodeByUrlAsync(urls: String?)
    fun subscribe(observer: Observer<in Episode>)
    fun unSubscribe()
}