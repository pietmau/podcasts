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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DownloadedPodcast) return false
        if (total != other.total) return false
        if (trackId != other.trackId) return false
        if (image != other.image) return false
        if (totalNumberofEpisodes != other.totalNumberofEpisodes) return false
        if (downloadedCount != other.downloadedCount) return false
        if (notDownloadedCount != other.notDownloadedCount) return false
        if (resources != other.resources) return false

        return true
    }

    override fun hashCode(): Int {
        var result = total ?: 0
        result = 31 * result + (trackId ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + totalNumberofEpisodes.hashCode()
        result = 31 * result + downloadedCount
        result = 31 * result + notDownloadedCount
        result = 31 * result + resources.hashCode()
        return result
    }


}
