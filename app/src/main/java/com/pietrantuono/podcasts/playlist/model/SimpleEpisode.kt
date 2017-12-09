package com.pietrantuono.podcasts.playlist.model

import android.os.Parcel
import android.os.Parcelable
import com.rometools.rome.feed.synd.SyndEnclosure
import models.pojos.Episode
import java.util.*

class SimpleEpisode(
        override var duration: String? = null,
        override var author: String? = null,
        override var isExplicit: Boolean? = null,
        override var imageUrl: String? = null,
        override var keywords: List<String>? = null,
        override var subtitle: String? = null,
        override var summary: String? = null,
        override var pubDate: Date? = null,
        override var title: String? = null,
        override var description: String? = null,
        override var enclosures: List<SyndEnclosure>? = null,
        override var downloaded: Boolean = false,
        override var link: String? = null,
        override var played: Boolean = false,
        override val durationInMills: Long? = null,
        override var filePathIncludingFileName: String? = null,
        override var fileSizeInBytes: Long = 0,
        override var downloadRequestId: Long = 0,
        override var uri: String? = null,
        override var deleted: Boolean = false) : Episode {

    constructor(parcel: Parcel) : this() {
        duration = parcel.readString()
        author = parcel.readString()
        isExplicit = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        imageUrl = parcel.readString()
        keywords = parcel.createStringArrayList()
        subtitle = parcel.readString()
        summary = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        downloaded = parcel.readByte() != 0.toByte()
        link = parcel.readString()
        played = parcel.readByte() != 0.toByte()
        filePathIncludingFileName = parcel.readString()
        fileSizeInBytes = parcel.readLong()
        downloadRequestId = parcel.readLong()
        uri = parcel.readString()
        deleted = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(duration)
        parcel.writeString(author)
        parcel.writeValue(isExplicit)
        parcel.writeString(imageUrl)
        parcel.writeStringList(keywords)
        parcel.writeString(subtitle)
        parcel.writeString(summary)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (downloaded) 1 else 0)
        parcel.writeString(link)
        parcel.writeByte(if (played) 1 else 0)
        parcel.writeString(filePathIncludingFileName)
        parcel.writeLong(fileSizeInBytes)
        parcel.writeLong(downloadRequestId)
        parcel.writeString(uri)
        parcel.writeByte(if (deleted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleEpisode> {
        override fun createFromParcel(parcel: Parcel): SimpleEpisode {
            return SimpleEpisode(parcel)
        }

        override fun newArray(size: Int): Array<SimpleEpisode?> {
            return arrayOfNulls(size)
        }
    }

}