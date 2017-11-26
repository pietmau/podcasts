package com.pietrantuono.podcasts.downloadfragment.view.custom

import com.pietrantuono.podcasts.R

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import models.pojos.Podcast

class DownloadedPodcast(
        val podcast: Podcast,
        title: String?,
        items: List<DownloadedEpisode>?,
        private val resources: ResourcesProvider) : ExpandableGroup<DownloadedEpisode>(title, items) {

    val downloadedCount: Int
    val notDownloadedCount: Int
    val total = podcast.episodes?.count()
    val trackId = podcast.trackId

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

