package com.pietrantuono.podcasts.apis


import com.rometools.rome.feed.synd.SyndEnclosure
import java.util.*

interface PodcastEpisode {

    var duration: String?

    var author: String?

    var isExplicit: Boolean?

    var imageUrl: String?

    var keywords: List<String>?

    var subtitle: String?

    var summary: String?

    var pubDate: Date?

    var title: String?

    var description: String?

    var enclosures: List<SyndEnclosure>?

    var downloaded: Boolean?

}