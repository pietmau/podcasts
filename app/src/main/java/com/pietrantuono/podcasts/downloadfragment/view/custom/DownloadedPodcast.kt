package com.pietrantuono.podcasts.downloadfragment.view.custom

import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class DownloadedPodcast(
        private val podcast: Podcast,
        tile: String?,
        items: List<DowloadedEpisode>?,
        private val resources: ResourcesProvider) : ExpandableGroup<DowloadedEpisode>(tile, items) {

    private val downloadedCount: Int
    private val notDownloadedCount: Int

    val downloadedCountText: String
        get() = " " + downloadedCount.toString() + " " + resources.getString(R.string.downloadedLowercase) + " "
    val notDownloadedCountText: String
        get() = " " + notDownloadedCount.toString() + " " + resources.getString(R.string.not_downlaoded) + " "

    val totalNumberofEpisodes: String
        get() = (podcast.episodes?.count() ?: 0).toString() + " " + resources.getString(R.string.episodes) + ": "

    init {
        downloadedCount = podcast.episodes?.filter { it.downloaded }?.count() ?: 0
        notDownloadedCount = podcast.episodes?.filter { !it.downloaded }?.count() ?: 0
    }


}

