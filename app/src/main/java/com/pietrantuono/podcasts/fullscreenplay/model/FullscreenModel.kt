package com.pietrantuono.podcasts.fullscreenplay.model


import models.pojos.Episode
import rx.Observer


interface FullscreenModel {
    var episode: Episode?
    fun getEpisodeByUriAsync(uri: String?)
    fun subscribe(observer: Observer<in Episode?>)
    fun unSubscribe()
}