package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast


interface DownloadView {
    fun setPodcasts(feed: List<Podcast>)
}