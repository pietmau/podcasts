package pojos

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import pojos.Episode
import pojos.Podcast
import utils.RealmUtlis
import java.lang.Exception
import java.lang.UnsupportedOperationException
import java.util.*

class PodcastImpl : Podcast {
    override var isPodcastSubscribed: Boolean
        get() = throw Exception("Unsopported")
        set(value) {
            throw Exception("Unsopported")
        }

    override var episodes: List<Episode>? = null

    override var numberOfEpisodesDowloaded: Int?
        get() = throw UnsupportedOperationException("Not used")
        set(value) = throw UnsupportedOperationException("Not used")

    @SerializedName("wrapperType")
    @Expose
    override var wrapperType: String? = null

    @SerializedName("kind")
    @Expose
    override var kind: String? = null

    @SerializedName("collectionId")
    @Expose
    override var collectionId: Int? = null

    @SerializedName("trackId")
    @Expose
    override var trackId: Int? = null

    @SerializedName("artistName")
    @Expose
    override var artistName: String? = null

    @SerializedName("collectionName")
    @Expose
    override var collectionName: String? = null

    @SerializedName("trackName")
    @Expose
    override var trackName: String? = null

    @SerializedName("collectionCensoredName")
    @Expose
    override var collectionCensoredName: String? = null

    @SerializedName("trackCensoredName")
    @Expose
    override var trackCensoredName: String? = null

    @SerializedName("collectionViewUrl")
    @Expose
    override var collectionViewUrl: String? = null

    @SerializedName("feedUrl")
    @Expose
    override var feedUrl: String? = null

    @SerializedName("trackViewUrl")
    @Expose
    override var trackViewUrl: String? = null

    @SerializedName("artworkUrl30")
    @Expose
    override var artworkUrl30: String? = null

    @SerializedName("artworkUrl60")
    @Expose
    override var artworkUrl60: String? = null

    @SerializedName("artworkUrl100")
    @Expose
    override var artworkUrl100: String? = null

    @SerializedName("collectionPrice")
    @Expose
    override var collectionPrice: Double? = null

    @SerializedName("trackPrice")
    @Expose
    override var trackPrice: Double? = null

    @SerializedName("trackRentalPrice")
    @Expose
    override var trackRentalPrice: Int? = null

    @SerializedName("collectionHdPrice")
    @Expose
    override var collectionHdPrice: Int? = null

    @SerializedName("trackHdPrice")
    @Expose
    override var trackHdPrice: Int? = null

    @SerializedName("trackHdRentalPrice")
    @Expose
    override var trackHdRentalPrice: Int? = null

    @SerializedName("releaseDate")
    @Expose
    override var releaseDate: String? = null

    @SerializedName("collectionExplicitness")
    @Expose
    override var collectionExplicitness: String? = null

    @SerializedName("trackExplicitness")
    @Expose
    override var trackExplicitness: String? = null

    @SerializedName("trackCount")
    @Expose
    override var trackCount: Int? = null

    @SerializedName("country")
    @Expose
    override var country: String? = null

    @SerializedName("currency")
    @Expose
    override var currency: String? = null

    @SerializedName("primaryGenreName")
    @Expose
    override var primaryGenreName: String? = null

    @SerializedName("contentAdvisoryRating")
    @Expose
    override var contentAdvisoryRating: String? = null

    @SerializedName("artworkUrl600")
    @Expose
    override var artworkUrl600: String? = null

    @SerializedName("genreIds")
    @Expose
    override var genreIds: List<String>? = null

    @SerializedName("genres")
    @Expose
    override var genres: List<String>? = null

    private constructor(`in`: Parcel) {
        wrapperType = `in`.readString()
        kind = `in`.readString()
        collectionId = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        trackId = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        artistName = `in`.readString()
        collectionName = `in`.readString()
        trackName = `in`.readString()
        collectionCensoredName = `in`.readString()
        trackCensoredName = `in`.readString()
        collectionViewUrl = `in`.readString()
        feedUrl = `in`.readString()
        trackViewUrl = `in`.readString()
        artworkUrl30 = `in`.readString()
        artworkUrl60 = `in`.readString()
        artworkUrl100 = `in`.readString()
        collectionPrice = if (`in`.readByte().toInt() == 0x00) null else `in`.readDouble()
        trackPrice = if (`in`.readByte().toInt() == 0x00) null else `in`.readDouble()
        trackRentalPrice = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        collectionHdPrice = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        trackHdPrice = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        trackHdRentalPrice = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        releaseDate = `in`.readString()
        collectionExplicitness = `in`.readString()
        trackExplicitness = `in`.readString()
        trackCount = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        country = `in`.readString()
        currency = `in`.readString()
        primaryGenreName = `in`.readString()
        contentAdvisoryRating = `in`.readString()
        artworkUrl600 = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            genreIds = ArrayList<String>()
            `in`.readList(genreIds, String::class.java.classLoader)
        } else {
            genreIds = null
        }
        if (`in`.readByte().toInt() == 0x01) {
            genres = ArrayList<String>()
            `in`.readList(genres, String::class.java.classLoader)
        } else {
            genres = null
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(wrapperType)
        dest.writeString(kind)
        if (collectionId == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(collectionId!!)
        }
        if (trackId == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(trackId!!)
        }
        dest.writeString(artistName)
        dest.writeString(collectionName)
        dest.writeString(trackName)
        dest.writeString(collectionCensoredName)
        dest.writeString(trackCensoredName)
        dest.writeString(collectionViewUrl)
        dest.writeString(feedUrl)
        dest.writeString(trackViewUrl)
        dest.writeString(artworkUrl30)
        dest.writeString(artworkUrl60)
        dest.writeString(artworkUrl100)
        if (collectionPrice == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeDouble(collectionPrice!!)
        }
        if (trackPrice == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeDouble(trackPrice!!)
        }
        if (trackRentalPrice == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(trackRentalPrice!!)
        }
        if (collectionHdPrice == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(collectionHdPrice!!)
        }
        if (trackHdPrice == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(trackHdPrice!!)
        }
        if (trackHdRentalPrice == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(trackHdRentalPrice!!)
        }
        dest.writeString(releaseDate)
        dest.writeString(collectionExplicitness)
        dest.writeString(trackExplicitness)
        if (trackCount == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(trackCount!!)
        }
        dest.writeString(country)
        dest.writeString(currency)
        dest.writeString(primaryGenreName)
        dest.writeString(contentAdvisoryRating)
        dest.writeString(artworkUrl600)
        if (genreIds == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(genreIds)
        }
        if (genres == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(genres)
        }
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Podcast> = object : Parcelable.Creator<Podcast> {
            override fun createFromParcel(`in`: Parcel): Podcast {
                return PodcastImpl(`in`)
            }

            override fun newArray(size: Int): Array<Podcast?> {
                return arrayOfNulls<Podcast>(size)
            }
        }
    }

    fun toRealm() {
        RealmUtlis.toSinglePodcastRealm(this)
    }
}