package com.pietrantuono.podcasts.fullscreenplay.model


import models.pojos.Episode
import rx.Observer


interface FullscreenModel {
    var episode: Episode?
    fun getEpisodeByTitleAsync(urls: String?)
    fun subscribe(observer: Observer<in Episode>)
    fun unSubscribe()
}