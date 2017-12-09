package com.pietrantuono.podcasts.playlist.model

import android.os.Parcel
import com.rometools.rome.feed.synd.SyndEnclosure
import models.pojos.Episode
import java.util.*


class SimpleEpisode:Episode {
    override var duration: String?
    override var author: String?
    override var isExplicit: Boolean?
    override var imageUrl: String?
    override var keywords: List<String>?
    override var subtitle: String?
    override var summary: String?
    override var pubDate: Date?
    override var title: String?
    override var description: String?
    override var enclosures: List<SyndEnclosure>?
    override var downloaded: Boolean
    override var link: String?
    override var played: Boolean
    override val durationInMills: Long?
    override var filePathIncludingFileName: String?
    override var fileSizeInBytes: Long
    override var downloadRequestId: Long
    override var uri: String?
    override var deleted: Boolean

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented")
    }

    override fun describeContents(): Int {
        TODO("not implemented")
    }
}