package com.pietrantuono.podcasts.interfaces


import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleEnclosure
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.providers.RealmString
import com.rometools.rome.feed.synd.SyndEnclosure
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.util.*

open class RealmPodcastEpisode : RealmObject, PodcastEpisode {
    override var downloaded: Boolean? = false
    @Ignore
    override var keywords: List<String>? = null
        get() = returnKeywords()
    override var duration: String? = null
    override var author: String? = null
    override var isExplicit: Boolean? = null
    override var imageUrl: String? = null
    private var realmKeywords: RealmList<RealmString>? = null
    override var subtitle: String? = null
    override var summary: String? = null
    override var pubDate: Date? = null
    override var title: String? = null
    override var description: String? = null
    private var syndEnclosures: RealmList<SimpleEnclosure>? = null
    @Ignore
    override var enclosures: List<SyndEnclosure>? = null
        get() = syndEnclosures

    constructor(duration: String?, author: String?, isExplicit: Boolean?, imageUrl: String?,
                keywords: List<String>?, subtitle: String?, summary: String?, pubDate: Date?,
                title: String?, description: String?, syndEnclosures: List<SyndEnclosure>?) {
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

    constructor()

    private fun returnKeywords(): List<String> {
        val list = mutableListOf<String>()
        for (realmSting in realmKeywords!!) {
            list.add(realmSting.string)
        }
        return list
    }

    private fun parseKeywords(keywords: List<String>?): RealmList<RealmString> {
        val realmStrings = RealmList<RealmString>()
        if (keywords == null) {
            return realmStrings
        }
        for (keyword in keywords) {
            realmStrings.add(RealmString(keyword))
        }
        return realmStrings
    }

    private fun parseEnclosures(syndEnclosures: List<SyndEnclosure>?): RealmList<SimpleEnclosure> {
        val result = RealmList<SimpleEnclosure>()
        if (syndEnclosures == null) {
            return result
        }
        for (enc in syndEnclosures) {
            result.add(SimpleEnclosure(enc))
        }
        return result
    }
}
