package com.pietrantuono.podcasts.downloadfragment.view.custom

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class DownloadedPodcast(private val podcast: Podcast, tile: String?, items: List<DowloadedEpisode>?)
    : ExpandableGroup<DowloadedEpisode>(tile, items) {

    val dowloandecCount: Int?
    val notDowloandecCount: Int?

    init {
        dowloandecCount = podcast.episodes?.filter { !it.downloaded }?.count()
        notDowloandecCount = podcast.episodes?.filter { it.downloaded }?.count()
    }
}

