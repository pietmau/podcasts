package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.apis.Episode
import rx.Observable
import rx.Observer


interface FullscreenModel {
    fun getEpisodeByUrl(urls: String?)
    fun subscribe(observer: Observer<in Episode>)
    fun unSubscribe()
}