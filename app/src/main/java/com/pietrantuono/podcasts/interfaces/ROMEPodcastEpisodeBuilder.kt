package com.pietrantuono.podcasts.interfaces

import com.rometools.rome.feed.synd.SyndEnclosure
import java.util.*

class ROMEPodcastEpisodeBuilder {
    private var duration: String? = null
    private var author: String? = null
    private var isExplicit: Boolean? = false
    private var imageUrl: String? = null
    private var keywords: List<String>? = null
    private var subtitle: String? = null
    private var summary: String? = null
    private var pubDate: Date? = null
    private var title: String? = null
    private var description: String? = null
    private var syndEnclosures: List<SyndEnclosure>? = null
    private var link: String? = null

    fun setDuration(duration: String?): ROMEPodcastEpisodeBuilder {
        this.duration = duration
        return this
    }

    fun setAuthor(author: String?): ROMEPodcastEpisodeBuilder {
        this.author = author
        return this
    }

    fun setIsExplicit(isExplicit: Boolean?): ROMEPodcastEpisodeBuilder {
        this.isExplicit = isExplicit
        return this
    }

    fun setImageUrl(imageUrl: String?): ROMEPodcastEpisodeBuilder {
        this.imageUrl = imageUrl
        return this
    }

    fun setKeywords(keywords: List<String>?): ROMEPodcastEpisodeBuilder {
        this.keywords = keywords
        return this
    }

    fun setSubtitle(subtitle: String?): ROMEPodcastEpisodeBuilder {
        this.subtitle = subtitle
        return this
    }

    fun setSummary(summary: String?): ROMEPodcastEpisodeBuilder {
        this.summary = summary
        return this
    }

    fun setPubDate(pubDate: Date?): ROMEPodcastEpisodeBuilder {
        this.pubDate = pubDate
        return this
    }

    fun setTitle(title: String?): ROMEPodcastEpisodeBuilder {
        this.title = title
        return this
    }

    fun setDescription(description: String?): ROMEPodcastEpisodeBuilder {
        this.description = description
        return this
    }

    fun setEnclosures(syndEnclosures: List<SyndEnclosure>?): ROMEPodcastEpisodeBuilder {
        this.syndEnclosures = syndEnclosures
        return this
    }

    fun setLink(link: String?): ROMEPodcastEpisodeBuilder {
        this.link = link
        return this
    }

    fun createROMEPodcastEpisode(): RealmEpisode {
        return RealmEpisode(duration, author, isExplicit, imageUrl, keywords, subtitle, summary, pubDate, title, description, syndEnclosures, link)
    }

}