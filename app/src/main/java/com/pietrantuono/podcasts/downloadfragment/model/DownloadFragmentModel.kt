package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import rx.Observer

interface DownloadFragmentModel {
    fun unsubscribe()
    fun subscribe(observer: Observer<List<Podcast>>)


}