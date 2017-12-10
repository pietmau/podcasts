package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import models.pojos.PodcastRealm
import rx.Observer

interface DownloadFragmentModel {
    fun unsubscribe()
    fun subscribe(observer: Observer<List<DownloadedPodcast>>)
    fun getPodcastTitleAsync(simpleObserver: Observer<String?>, trackId: Int?)
    fun getSubscedPodcasts(): List<PodcastRealm>
}