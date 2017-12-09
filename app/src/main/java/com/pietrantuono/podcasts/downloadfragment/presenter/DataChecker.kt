package com.pietrantuono.podcasts.downloadfragment.presenter

import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast

class DataChecker {
    var feed: MutableList<DownloadedPodcast> = mutableListOf()
    var presenter: DownloadFragmentPresenter? = null

    val thereIsNoPreviousData: Boolean
        get() = this.feed == null || this.feed?.isEmpty() == true

    fun onDataReceived(feed: List<DownloadedPodcast>) {
        if (feed == null || feed.isEmpty()) {
            return
        }
        if (thereIsNoPreviousData) {
            onNewData(feed)
            return
        }
        onDataUpdate(feed)
    }

    fun onDataUpdate(feed: List<DownloadedPodcast>?) {
        if (feed == null || feed?.size != this.feed.size) {
            return
        }
        iterateOverFeeds(feed)
    }

    private fun iterateOverFeeds(copy: List<DownloadedPodcast>) {
        for (i in feed.indices) {
            val feedEpisodes = feed[i].items
            val copyEpisodes = copy[i].items
            if (copyEpisodes == null || feedEpisodes == null || copyEpisodes.size != feedEpisodes.size) {
                return
            }
            iterateOverEpisodes(feedEpisodes, i, copyEpisodes)
        }
    }

    private fun iterateOverEpisodes(feedEpisodes: MutableList<DownloadedEpisode>, i: Int, copyEpisodes: MutableList<DownloadedEpisode>) {
        for (j in feedEpisodes.indices) {
            if (feedEpisodes[j].downloaded != copyEpisodes[j].downloaded) {
                presenter?.updateEpisode(i, j, copyEpisodes[j])
                feed?.get(i)?.items?.set(j, copyEpisodes[j])
            }
        }
    }

    private fun onNewData(feed: List<DownloadedPodcast>) {
        this.feed.clear()
        this.feed.addAll(feed)
        presenter?.onNewData(feed)
    }

}
