package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.apis.Episode
import io.reactivex.observers.DisposableObserver


interface FullscreenModel {
    var episode: Episode?
    fun getEpisodeByUrlAsync(urls: String?)
    fun subscribe(observer: DisposableObserver<in Episode>)
    fun unSubscribe()
}