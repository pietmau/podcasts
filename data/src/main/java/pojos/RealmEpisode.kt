package pojos

import android.os.Parcel
import android.os.Parcelable
import com.rometools.rome.feed.synd.SyndEnclosure
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

open class RealmEpisode : RealmObject, Episode {
    @Ignore private val FORMAT = "HH:mm:ss"
    @Ignore private val EPOCH = "00:00:00"
    override val durationInMills: Long?
        get() = duration?.let {
            try {
                val simpleDateFormat = SimpleDateFormat(FORMAT)
                simpleDateFormat.parse(it).time - simpleDateFormat.parse(EPOCH).time
            } catch (exception: Exception) {
                null
            }
        }
    override var played: Boolean = false
    @PrimaryKey
    override var link: String? = null
    override var downloaded: Boolean = false
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
    override var filePathIncludingFileName: String? = null
    override var fileSizeInBytes: Long = 0
    override var downloadRequestId: Long = -1

    constructor(parcel: Parcel) {
        link = parcel.readString()
        downloaded = parcel.readByte() != 0.toByte()
        duration = parcel.readString()
        author = parcel.readString()
        isExplicit = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        imageUrl = parcel.readString()
        subtitle = parcel.readString()
        summary = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        fileSizeInBytes = parcel.readLong()
        downloadRequestId = parcel.readLong()
    }

    constructor()

    constructor(duration: String?, author: String?, isExplicit: Boolean?, imageUrl: String?,
                keywords: List<String>?, subtitle: String?, summary: String?, pubDate: Date?,
                title: String?, description: String?, syndEnclosures: List<SyndEnclosure>?,
                link: String?) {
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
        this.link = link
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(link)
        parcel.writeByte(if (downloaded) 1 else 0)
        parcel.writeString(duration)
        parcel.writeString(author)
        parcel.writeValue(isExplicit)
        parcel.writeString(imageUrl)
        parcel.writeString(subtitle)
        parcel.writeString(summary)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeLong(fileSizeInBytes)
        parcel.writeLong(downloadRequestId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RealmEpisode) return false
        if (link != other.link) return false
        return true
    }

    override fun hashCode(): Int {
        return link?.hashCode() ?: 0
    }

    companion object CREATOR : Parcelable.Creator<RealmEpisode> {
        override fun createFromParcel(parcel: Parcel): RealmEpisode {
            return RealmEpisode(parcel)
        }

        override fun newArray(size: Int): Array<RealmEpisode?> {
            return arrayOfNulls(size)
        }
    }
}