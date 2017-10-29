package com.pietrantuono.podcasts.apis

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastImpl
import com.pietrantuono.podcasts.addpodcast.model.pojos.TRACK_ID
import com.rometools.rome.feed.synd.SyndEnclosure
import java.text.SimpleDateFormat
import java.util.*

const val PODCAST_ID = "podcastId"

@Entity(foreignKeys =
arrayOf(ForeignKey(entity = PodcastImpl::class, parentColumns = arrayOf(TRACK_ID), childColumns = arrayOf(PODCAST_ID))))
class RoomEpisode(
        override var duration: String? = null,
        override var author: String? = null,
        override var isExplicit: Boolean? = false,
        override var imageUrl: String? = null,
        override var keywords: List<String>? = null,
        override var subtitle: String? = null,
        override var summary: String? = null,
        override var pubDate: Date? = null,
        override var title: String? = null,
        override var description: String? = null,
        @PrimaryKey
        override var link: String? = null,
        override var enclosures: List<SyndEnclosure>?) : Episode {
    private val FORMAT = "HH:mm:ss"
    private val EPOCH = "00:00:00"

    @ColumnInfo(name = PODCAST_ID)
    private var podcastId: Int? = null

    override var downloaded: Boolean = false
    override var played: Boolean = false
    override val durationInMills: Long?
        get() = duration?.let {
            try {
                val simpleDateFormat = SimpleDateFormat(FORMAT)
                simpleDateFormat.parse(it).time - simpleDateFormat.parse(EPOCH).time
            } catch (exception: Exception) {
                null
            }
        }
    override var filePath: String? = null

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readString(),
            null,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            null) {
        downloaded = parcel.readByte() != 0.toByte()
        played = parcel.readByte() != 0.toByte()
        filePath = parcel.readString()
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
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomEpisode> {

        override fun createFromParcel(parcel: Parcel): RoomEpisode {
            return RoomEpisode(parcel)
        }

        override fun newArray(size: Int): Array<RoomEpisode?> {
            return arrayOfNulls(size)
        }
    }


}