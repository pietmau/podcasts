package com.pietrantuono.podcasts.addpodcast.model.pojos

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import java.util.*

class SinglePodcastImpl : SinglePodcast {
    override var episodes: List<PodcastEpisodeModel>? = null

    /**

     * @return
     * *     The wrapperType
     */
    /**

     * @param wrapperType
     * *     The wrapperType
     */
    @SerializedName("wrapperType")
    @Expose
    override var wrapperType: String? = null
    /**

     * @return
     * *     The kind
     */
    /**

     * @param kind
     * *     The kind
     */
    @SerializedName("kind")
    @Expose
    override var kind: String? = null
    /**

     * @return
     * *     The collectionId
     */
    /**

     * @param collectionId
     * *     The collectionId
     */
    @SerializedName("collectionId")
    @Expose
    override var collectionId: Int? = null
    /**

     * @return
     * *     The trackId
     */
    /**

     * @param trackId
     * *     The trackId
     */
    @SerializedName("trackId")
    @Expose
    override var trackId: Int? = null
    /**

     * @return
     * *     The artistName
     */
    /**

     * @param artistName
     * *     The artistName
     */
    @SerializedName("artistName")
    @Expose
    override var artistName: String? = null
    /**

     * @return
     * *     The collectionName
     */
    /**

     * @param collectionName
     * *     The collectionName
     */
    @SerializedName("collectionName")
    @Expose
    override var collectionName: String? = null
    /**

     * @return
     * *     The trackName
     */
    /**

     * @param trackName
     * *     The trackName
     */
    @SerializedName("trackName")
    @Expose
    override var trackName: String? = null
    /**

     * @return
     * *     The collectionCensoredName
     */
    /**

     * @param collectionCensoredName
     * *     The collectionCensoredName
     */
    @SerializedName("collectionCensoredName")
    @Expose
    override var collectionCensoredName: String? = null
    /**

     * @return
     * *     The trackCensoredName
     */
    /**

     * @param trackCensoredName
     * *     The trackCensoredName
     */
    @SerializedName("trackCensoredName")
    @Expose
    override var trackCensoredName: String? = null
    /**

     * @return
     * *     The collectionViewUrl
     */
    /**

     * @param collectionViewUrl
     * *     The collectionViewUrl
     */
    @SerializedName("collectionViewUrl")
    @Expose
    override var collectionViewUrl: String? = null
    /**

     * @return
     * *     The feedUrl
     */
    /**

     * @param feedUrl
     * *     The feedUrl
     */
    @SerializedName("feedUrl")
    @Expose
    override var feedUrl: String? = null
    /**

     * @return
     * *     The trackViewUrl
     */
    /**

     * @param trackViewUrl
     * *     The trackViewUrl
     */
    @SerializedName("trackViewUrl")
    @Expose
    override var trackViewUrl: String? = null
    /**

     * @return
     * *     The artworkUrl30
     */
    /**

     * @param artworkUrl30
     * *     The artworkUrl30
     */
    @SerializedName("artworkUrl30")
    @Expose
    override var artworkUrl30: String? = null
    /**

     * @return
     * *     The artworkUrl60
     */
    /**

     * @param artworkUrl60
     * *     The artworkUrl60
     */
    @SerializedName("artworkUrl60")
    @Expose
    override var artworkUrl60: String? = null
    /**

     * @return
     * *     The artworkUrl100
     */
    /**

     * @param artworkUrl100
     * *     The artworkUrl100
     */
    @SerializedName("artworkUrl100")
    @Expose
    override var artworkUrl100: String? = null
    /**

     * @return
     * *     The collectionPrice
     */
    /**

     * @param collectionPrice
     * *     The collectionPrice
     */
    @SerializedName("collectionPrice")
    @Expose
    override var collectionPrice: Double? = null
    /**

     * @return
     * *     The trackPrice
     */
    /**

     * @param trackPrice
     * *     The trackPrice
     */
    @SerializedName("trackPrice")
    @Expose
    override var trackPrice: Double? = null
    /**

     * @return
     * *     The trackRentalPrice
     */
    /**

     * @param trackRentalPrice
     * *     The trackRentalPrice
     */
    @SerializedName("trackRentalPrice")
    @Expose
    override var trackRentalPrice: Int? = null
    /**

     * @return
     * *     The collectionHdPrice
     */
    /**

     * @param collectionHdPrice
     * *     The collectionHdPrice
     */
    @SerializedName("collectionHdPrice")
    @Expose
    override var collectionHdPrice: Int? = null
    /**

     * @return
     * *     The trackHdPrice
     */
    /**

     * @param trackHdPrice
     * *     The trackHdPrice
     */
    @SerializedName("trackHdPrice")
    @Expose
    override var trackHdPrice: Int? = null
    /**

     * @return
     * *     The trackHdRentalPrice
     */
    /**

     * @param trackHdRentalPrice
     * *     The trackHdRentalPrice
     */
    @SerializedName("trackHdRentalPrice")
    @Expose
    override var trackHdRentalPrice: Int? = null
    /**

     * @return
     * *     The releaseDate
     */
    /**

     * @param releaseDate
     * *     The releaseDate
     */
    @SerializedName("releaseDate")
    @Expose
    override var releaseDate: String? = null
    /**

     * @return
     * *     The collectionExplicitness
     */
    /**

     * @param collectionExplicitness
     * *     The collectionExplicitness
     */
    @SerializedName("collectionExplicitness")
    @Expose
    override var collectionExplicitness: String? = null
    /**

     * @return
     * *     The trackExplicitness
     */
    /**

     * @param trackExplicitness
     * *     The trackExplicitness
     */
    @SerializedName("trackExplicitness")
    @Expose
    override var trackExplicitness: String? = null
    /**

     * @return
     * *     The trackCount
     */
    /**

     * @param trackCount
     * *     The trackCount
     */
    @SerializedName("trackCount")
    @Expose
    override var trackCount: Int? = null
    /**

     * @return
     * *     The country
     */
    /**

     * @param country
     * *     The country
     */
    @SerializedName("country")
    @Expose
    override var country: String? = null
    /**

     * @return
     * *     The currency
     */
    /**

     * @param currency
     * *     The currency
     */
    @SerializedName("currency")
    @Expose
    override var currency: String? = null
    /**

     * @return
     * *     The primaryGenreName
     */
    /**

     * @param primaryGenreName
     * *     The primaryGenreName
     */
    @SerializedName("primaryGenreName")
    @Expose
    override var primaryGenreName: String? = null
    /**

     * @return
     * *     The contentAdvisoryRating
     */
    /**

     * @param contentAdvisoryRating
     * *     The contentAdvisoryRating
     */
    @SerializedName("contentAdvisoryRating")
    @Expose
    override var contentAdvisoryRating: String? = null
    /**

     * @return
     * *     The artworkUrl600
     */
    /**

     * @param artworkUrl600
     * *     The artworkUrl600
     */
    @SerializedName("artworkUrl600")
    @Expose
    override var artworkUrl600: String? = null

    @SerializedName("genreIds")
    @Expose
    override var genreIds: List<String>? = null

    @SerializedName("genres")
    @Expose
    override var genres: List<String>? = null

    val genresAsString: String
        get() {
            val stringBuilder = StringBuilder()
            if (genres != null) {
                val l = genres!!.size
                for (i in 0..l - 1) {
                    stringBuilder.append(genres!![i])
                    if (i < l - 1) {
                        stringBuilder.append(", ")
                    }
                }
            }
            return stringBuilder.toString()
        }

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
        @JvmField final val CREATOR: Parcelable.Creator<SinglePodcast> = object : Parcelable.Creator<SinglePodcast> {
            override fun createFromParcel(`in`: Parcel): SinglePodcast {
                return SinglePodcastImpl(`in`)
            }

            override fun newArray(size: Int): Array<SinglePodcast?> {
                return arrayOfNulls<SinglePodcast>(size)
            }
        }
    }
}