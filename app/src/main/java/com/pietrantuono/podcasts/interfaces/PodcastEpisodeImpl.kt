package com.pietrantuono.podcasts.interfaces


import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleEnclosure
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.providers.RealmString
import com.rometools.rome.feed.synd.SyndEnclosure
import io.realm.RealmList
import io.realm.RealmObject
import java.util.*

open class PodcastEpisodeImpl : RealmObject, PodcastEpisode {
    override var downloaded: Boolean = false
    override val keywords: List<String>
        get() = returnKeywords()
    override val duration: String
    override val author: String
    override val isExplicit: Boolean
    override val imageUrl: String
    private val realmKeywords: RealmList<RealmString>
    override val subtitle: String
    override val summary: String
    override val pubDate: Date
    override val title: String
    override val description: String
    private val syndEnclosures: RealmList<SimpleEnclosure>

    constructor(duration: String, author: String, isExplicit: Boolean, imageUrl: String, keywords: List<String>, subtitle: String, summary: String, pubDate: Date, title: String, description: String, syndEnclosures: List<SyndEnclosure>) {
        this.duration = duration
        this.author = author
        this.isExplicit = isExplicit
        this.imageUrl = imageUrl
        this.realmKeywords = parseKeywords(keywords)
        this.subtitle = subtitle
        this.summary = summary
        this.pubDate = pubDate
        this.title = title
        this.description = description
        this.syndEnclosures = parseEnclosures(syndEnclosures)
    }


    private fun returnKeywords(): List<String> {
        val list = mutableListOf<String>()
        for (realmSting in realmKeywords) {
            list.add(realmSting.string)
        }
        return list
    }

    private fun parseKeywords(keywords: List<String>): RealmList<RealmString> {
        val realmStrings = RealmList<RealmString>()
        for (keyword in keywords) {
            realmStrings.add(RealmString(keyword))
        }
        return realmStrings
    }

    private fun parseEnclosures(syndEnclosures: List<SyndEnclosure>): RealmList<SimpleEnclosure> {
        val result = RealmList<SimpleEnclosure>()
        for (enc in syndEnclosures) {
            result.add(SimpleEnclosure(enc))
        }
        return result
    }

    override val enclosures: List<SyndEnclosure>
        get() = syndEnclosures

}
