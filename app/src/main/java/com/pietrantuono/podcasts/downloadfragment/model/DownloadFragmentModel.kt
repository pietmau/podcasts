package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleDisposableObserver
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import io.reactivex.Observer

interface DownloadFragmentModel {
    fun unsubscribe()
    fun subscribe(observer: SimpleDisposableObserver<List<DownloadedPodcast>>)
    fun getEpisodeTitleAsync(observer: Observer<String?>, link: String?)
    fun getPodcastTitleAsync(simpleObserver: Observer<String?>, trackId: Int?)


}