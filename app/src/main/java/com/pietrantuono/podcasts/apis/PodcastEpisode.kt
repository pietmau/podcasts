package com.pietrantuono.podcasts.apis


import com.rometools.rome.feed.synd.SyndEnclosure
import java.util.*

interface PodcastEpisode {

    val duration: String

    val author: String

    val isExplicit: Boolean

    val imageUrl: String

    val keywords: List<String>

    val subtitle: String

    val summary: String

    val pubDate: Date

    val title: String

    val description: String

    val enclosures: List<SyndEnclosure>

    var downloaded: Boolean
}