package com.pietrantuono.podcasts.playlist.model

import com.rometools.rome.feed.synd.SyndEnclosure
import java.util.*

class SimpleEpisodeBuilder(
        var duration: String? = null,
        var author: String? = null,
        var isExplicit: Boolean? = null,
        var imageUrl: String? = null,
        var keywords: List<String>? = null,
        var subtitle: String? = null,
        var summary: String? = null,
        var pubDate: Date? = null,
        var title: String? = null,
        var description: String? = null,
        var enclosures: List<SyndEnclosure>? = null,
        var downloaded: Boolean = false,
        var link: String? = null,
        var played: Boolean = false,
        val durationInMills: Long? = null,
        var filePathIncludingFileName: String? = null,
        var fileSizeInBytes: Long = 0,
        var downloadRequestId: Long = 0,
        var uri: String? = null,
        var deleted: Boolean = false) {

    fun setDuration(duration: String?): SimpleEpisodeBuilder {
        this.duration = duration
        return this
    }

    fun setAuthor(author: String?): SimpleEpisodeBuilder {
        this.author = author
        return this
    }

    fun setIsExplicit(isExplicit: Boolean?): SimpleEpisodeBuilder {
        this.isExplicit = isExplicit
        return this
    }

    fun setImageUrl(imageUrl: String?): SimpleEpisodeBuilder {
        this.imageUrl = imageUrl
        return this
    }

    fun setKeywords(keywords: List<String>?): SimpleEpisodeBuilder {
        this.keywords = keywords
        return this
    }

    fun setSubtitle(subtitle: String?): SimpleEpisodeBuilder {
        this.subtitle = subtitle
        return this
    }

    fun setSummary(summary: String?): SimpleEpisodeBuilder {
        this.summary = summary
        return this
    }

    fun setPubDate(pubDate: Date?): SimpleEpisodeBuilder {
        this.pubDate = pubDate
        return this
    }

    fun setTitle(title: String?): SimpleEpisodeBuilder {
        this.title = title
        return this
    }

    fun setDescription(description: String?): SimpleEpisodeBuilder {
        this.description = description
        return this
    }

    fun setEnclosures(enclosures: List<SyndEnclosure>?): SimpleEpisodeBuilder {
        this.enclosures = enclosures
        return this
    }

    fun setDownloaded(downloaded: Boolean): SimpleEpisodeBuilder {
        this.downloaded = downloaded
        return this
    }

    fun setLink(link: String?): SimpleEpisodeBuilder {
        this.link = link
        return this
    }

    fun setPlayed(played: Boolean): SimpleEpisodeBuilder {
        this.played = played
        return this
    }

//    fun setPlayed(played: Boolean): SimpleEpisodeBuilder {
//        this.played = played
//        return this
//    }
}