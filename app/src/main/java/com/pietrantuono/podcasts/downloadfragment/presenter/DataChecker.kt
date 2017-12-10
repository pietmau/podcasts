package com.pietrantuono.podcasts.downloadfragment.presenter

import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast

class DataChecker {
    var currentFeed: MutableList<DownloadedPodcast> = mutableListOf()
    var presenter: DownloadFragmentPresenter? = null


    fun onDataReceived(newFeed: List<DownloadedPodcast>) {
        if (newFeed == null || newFeed.isEmpty()) {
            return
        }
        if (thereIsNoPreviousData()) {
            onNewData(newFeed)
            return
        }
        onDataUpdate(newFeed)
    }

    fun onDataUpdate(feed: List<DownloadedPodcast>?) {
        if (feed == null || feed?.size != this.currentFeed.size) {
            return
        }
        iterateOverFeeds(feed)
    }

    private fun iterateOverFeeds(newFeed: List<DownloadedPodcast>) {
        for (i in currentFeed.indices) {
            val currentPodcast = currentFeed[i]
            val newPodcast = newFeed[i]
            checkEpisodes(currentPodcast, newPodcast, i)
            checkPodcasts(currentPodcast, newPodcast, i)
        }
    }

    private fun checkPodcasts(currentPodcast: DownloadedPodcast, newPodcast: DownloadedPodcast, i: Int) {
        if (currentPodcast != newPodcast) {
            presenter?.updatePodcast(i, newPodcast)
            currentFeed?.set(i, newPodcast)
        }
    }

    private fun checkEpisodes(currentPodcast: DownloadedPodcast, newPodcast: DownloadedPodcast, i: Int) {
        val newEpisodes = newPodcast.items
        val currentEpisodes = currentPodcast.items
        if (newEpisodes == null || currentEpisodes == null || newEpisodes.size != currentEpisodes.size) {
            return
        }
        for (j in currentEpisodes.indices) {
            if (currentEpisodes[j].downloaded != newEpisodes[j].downloaded) {
                presenter?.updateEpisode(i, j, newEpisodes[j])
                currentFeed?.get(i)?.items?.set(j, newEpisodes[j])
            }
        }
    }

    private fun onNewData(feed: List<DownloadedPodcast>) {
        this.currentFeed.clear()
        this.currentFeed.addAll(feed)
        presenter?.onNewData(feed)
    }

    fun thereIsNoPreviousData(): Boolean = this.currentFeed == null || this.currentFeed?.isEmpty() == true

}
