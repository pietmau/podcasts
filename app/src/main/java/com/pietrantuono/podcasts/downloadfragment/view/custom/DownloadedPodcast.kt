package com.pietrantuono.podcasts.downloadfragment.view.custom

import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import models.pojos.Podcast

class DownloadedPodcast(
        title: String?,
        val total: Int?,
        val trackId: Int?,
        val image: String?,
        items: List<DownloadedEpisode>?,
        val totalNumberofEpisodes: String,
        val downloadedCount: Int,
        val notDownloadedCount: Int,
        val resources: ResourcesProvider
) : ExpandableGroup<DownloadedEpisode>(title, items) {

    companion object {
        fun fromPodcast(podcast: Podcast, title: String?, items: List<DownloadedEpisode>?, resources: ResourcesProvider): DownloadedPodcast {
            val total = podcast.episodes?.count()
            val trrackId = podcast.trackId
            val image = podcast.artworkUrl30
            val dowloaded = podcast.episodes?.filter { it.downloaded }?.count() ?: 0
            val notDownloadedCount = podcast.episodes?.filter { !it.downloaded }?.count() ?: 0
            val number = (podcast.episodes?.count() ?: 0).toString() + " " + resources.getString(R.string.episodes) + ": "
            return DownloadedPodcast(title, total, trrackId, image, items, number, dowloaded, notDownloadedCount, resources)
        }
    }

    val downloadedCountText: String
        get() = " " + downloadedCount.toString() + "/" + total + " " + resources.getString(R.string.downloadedLowercase) + " "
    val notDownloadedCountText: String
        get() = " " + notDownloadedCount.toString() + "/" + total + " " + resources.getString(R.string.not_downlaoded) + " "
}
