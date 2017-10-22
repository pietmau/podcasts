package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
}