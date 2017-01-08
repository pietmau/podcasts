
package com.pietrantuono.podcasts.addpodcast.model.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SinglePodcast implements Parcelable {

    @SerializedName("wrapperType")
    @Expose
    private String wrapperType;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("collectionId")
    @Expose
    private Integer collectionId;
    @SerializedName("trackId")
    @Expose
    private Integer trackId;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("collectionName")
    @Expose
    private String collectionName;
    @SerializedName("trackName")
    @Expose
    private String trackName;
    @SerializedName("collectionCensoredName")
    @Expose
    private String collectionCensoredName;
    @SerializedName("trackCensoredName")
    @Expose
    private String trackCensoredName;
    @SerializedName("collectionViewUrl")
    @Expose
    private String collectionViewUrl;
    @SerializedName("feedUrl")
    @Expose
    private String feedUrl;
    @SerializedName("trackViewUrl")
    @Expose
    private String trackViewUrl;
    @SerializedName("artworkUrl30")
    @Expose
    private String artworkUrl30;
    @SerializedName("artworkUrl60")
    @Expose
    private String artworkUrl60;
    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;
    @SerializedName("collectionPrice")
    @Expose
    private Double collectionPrice;
    @SerializedName("trackPrice")
    @Expose
    private Double trackPrice;
    @SerializedName("trackRentalPrice")
    @Expose
    private Integer trackRentalPrice;
    @SerializedName("collectionHdPrice")
    @Expose
    private Integer collectionHdPrice;
    @SerializedName("trackHdPrice")
    @Expose
    private Integer trackHdPrice;
    @SerializedName("trackHdRentalPrice")
    @Expose
    private Integer trackHdRentalPrice;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("collectionExplicitness")
    @Expose
    private String collectionExplicitness;
    @SerializedName("trackExplicitness")
    @Expose
    private String trackExplicitness;
    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;
    @SerializedName("contentAdvisoryRating")
    @Expose
    private String contentAdvisoryRating;
    @SerializedName("artworkUrl600")
    @Expose
    private String artworkUrl600;
    @SerializedName("genreIds")
    @Expose
    private List<String> genreIds = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;

    /**
     *
     * @return
     *     The wrapperType
     */
    public String getWrapperType() {
        return wrapperType;
    }

    /**
     *
     * @param wrapperType
     *     The wrapperType
     */
    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    /**
     *
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     *     The collectionId
     */
    public Integer getCollectionId() {
        return collectionId;
    }

    /**
     *
     * @param collectionId
     *     The collectionId
     */
    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    /**
     *
     * @return
     *     The trackId
     */
    public Integer getTrackId() {
        return trackId;
    }

    /**
     *
     * @param trackId
     *     The trackId
     */
    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    /**
     *
     * @return
     *     The artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     *
     * @param artistName
     *     The artistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     *
     * @return
     *     The collectionName
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     *
     * @param collectionName
     *     The collectionName
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     *
     * @return
     *     The trackName
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     *
     * @param trackName
     *     The trackName
     */
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /**
     *
     * @return
     *     The collectionCensoredName
     */
    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    /**
     *
     * @param collectionCensoredName
     *     The collectionCensoredName
     */
    public void setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
    }

    /**
     *
     * @return
     *     The trackCensoredName
     */
    public String getTrackCensoredName() {
        return trackCensoredName;
    }

    /**
     *
     * @param trackCensoredName
     *     The trackCensoredName
     */
    public void setTrackCensoredName(String trackCensoredName) {
        this.trackCensoredName = trackCensoredName;
    }

    /**
     *
     * @return
     *     The collectionViewUrl
     */
    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    /**
     *
     * @param collectionViewUrl
     *     The collectionViewUrl
     */
    public void setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
    }

    /**
     *
     * @return
     *     The feedUrl
     */
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     *
     * @param feedUrl
     *     The feedUrl
     */
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    /**
     *
     * @return
     *     The trackViewUrl
     */
    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    /**
     *
     * @param trackViewUrl
     *     The trackViewUrl
     */
    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    /**
     *
     * @return
     *     The artworkUrl30
     */
    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    /**
     *
     * @param artworkUrl30
     *     The artworkUrl30
     */
    public void setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
    }

    /**
     *
     * @return
     *     The artworkUrl60
     */
    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    /**
     *
     * @param artworkUrl60
     *     The artworkUrl60
     */
    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    /**
     *
     * @return
     *     The artworkUrl100
     */
    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    /**
     *
     * @param artworkUrl100
     *     The artworkUrl100
     */
    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    /**
     *
     * @return
     *     The collectionPrice
     */
    public Double getCollectionPrice() {
        return collectionPrice;
    }

    /**
     *
     * @param collectionPrice
     *     The collectionPrice
     */
    public void setCollectionPrice(Double collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    /**
     *
     * @return
     *     The trackPrice
     */
    public Double getTrackPrice() {
        return trackPrice;
    }

    /**
     *
     * @param trackPrice
     *     The trackPrice
     */
    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    /**
     *
     * @return
     *     The trackRentalPrice
     */
    public Integer getTrackRentalPrice() {
        return trackRentalPrice;
    }

    /**
     *
     * @param trackRentalPrice
     *     The trackRentalPrice
     */
    public void setTrackRentalPrice(Integer trackRentalPrice) {
        this.trackRentalPrice = trackRentalPrice;
    }

    /**
     *
     * @return
     *     The collectionHdPrice
     */
    public Integer getCollectionHdPrice() {
        return collectionHdPrice;
    }

    /**
     *
     * @param collectionHdPrice
     *     The collectionHdPrice
     */
    public void setCollectionHdPrice(Integer collectionHdPrice) {
        this.collectionHdPrice = collectionHdPrice;
    }

    /**
     *
     * @return
     *     The trackHdPrice
     */
    public Integer getTrackHdPrice() {
        return trackHdPrice;
    }

    /**
     *
     * @param trackHdPrice
     *     The trackHdPrice
     */
    public void setTrackHdPrice(Integer trackHdPrice) {
        this.trackHdPrice = trackHdPrice;
    }

    /**
     *
     * @return
     *     The trackHdRentalPrice
     */
    public Integer getTrackHdRentalPrice() {
        return trackHdRentalPrice;
    }

    /**
     *
     * @param trackHdRentalPrice
     *     The trackHdRentalPrice
     */
    public void setTrackHdRentalPrice(Integer trackHdRentalPrice) {
        this.trackHdRentalPrice = trackHdRentalPrice;
    }

    /**
     *
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     *
     * @param releaseDate
     *     The releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return
     *     The collectionExplicitness
     */
    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    /**
     *
     * @param collectionExplicitness
     *     The collectionExplicitness
     */
    public void setCollectionExplicitness(String collectionExplicitness) {
        this.collectionExplicitness = collectionExplicitness;
    }

    /**
     *
     * @return
     *     The trackExplicitness
     */
    public String getTrackExplicitness() {
        return trackExplicitness;
    }

    /**
     *
     * @param trackExplicitness
     *     The trackExplicitness
     */
    public void setTrackExplicitness(String trackExplicitness) {
        this.trackExplicitness = trackExplicitness;
    }

    /**
     *
     * @return
     *     The trackCount
     */
    public Integer getTrackCount() {
        return trackCount;
    }

    /**
     *
     * @param trackCount
     *     The trackCount
     */
    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    /**
     *
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     *     The primaryGenreName
     */
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    /**
     *
     * @param primaryGenreName
     *     The primaryGenreName
     */
    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    /**
     *
     * @return
     *     The contentAdvisoryRating
     */
    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    /**
     *
     * @param contentAdvisoryRating
     *     The contentAdvisoryRating
     */
    public void setContentAdvisoryRating(String contentAdvisoryRating) {
        this.contentAdvisoryRating = contentAdvisoryRating;
    }

    /**
     *
     * @return
     *     The artworkUrl600
     */
    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    /**
     *
     * @param artworkUrl600
     *     The artworkUrl600
     */
    public void setArtworkUrl600(String artworkUrl600) {
        this.artworkUrl600 = artworkUrl600;
    }

    /**
     *
     * @return
     *     The genreIds
     */
    public List<String> getGenreIds() {
        return genreIds;
    }

    /**
     *
     * @param genreIds
     *     The genreIds
     */
    public void setGenreIds(List<String> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     *
     * @return
     *     The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     *     The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getGenresAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        int l = getGenres().size();
        for (int i = 0; i < l; i++) {
            stringBuilder.append(getGenres().get(i));
            if (i < l - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private SinglePodcast(Parcel in) {
        wrapperType = in.readString();
        kind = in.readString();
        collectionId = in.readByte() == 0x00 ? null : in.readInt();
        trackId = in.readByte() == 0x00 ? null : in.readInt();
        artistName = in.readString();
        collectionName = in.readString();
        trackName = in.readString();
        collectionCensoredName = in.readString();
        trackCensoredName = in.readString();
        collectionViewUrl = in.readString();
        feedUrl = in.readString();
        trackViewUrl = in.readString();
        artworkUrl30 = in.readString();
        artworkUrl60 = in.readString();
        artworkUrl100 = in.readString();
        collectionPrice = in.readByte() == 0x00 ? null : in.readDouble();
        trackPrice = in.readByte() == 0x00 ? null : in.readDouble();
        trackRentalPrice = in.readByte() == 0x00 ? null : in.readInt();
        collectionHdPrice = in.readByte() == 0x00 ? null : in.readInt();
        trackHdPrice = in.readByte() == 0x00 ? null : in.readInt();
        trackHdRentalPrice = in.readByte() == 0x00 ? null : in.readInt();
        releaseDate = in.readString();
        collectionExplicitness = in.readString();
        trackExplicitness = in.readString();
        trackCount = in.readByte() == 0x00 ? null : in.readInt();
        country = in.readString();
        currency = in.readString();
        primaryGenreName = in.readString();
        contentAdvisoryRating = in.readString();
        artworkUrl600 = in.readString();
        if (in.readByte() == 0x01) {
            genreIds = new ArrayList<String>();
            in.readList(genreIds, String.class.getClassLoader());
        } else {
            genreIds = null;
        }
        if (in.readByte() == 0x01) {
            genres = new ArrayList<String>();
            in.readList(genres, String.class.getClassLoader());
        } else {
            genres = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wrapperType);
        dest.writeString(kind);
        if (collectionId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(collectionId);
        }
        if (trackId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(trackId);
        }
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeString(trackName);
        dest.writeString(collectionCensoredName);
        dest.writeString(trackCensoredName);
        dest.writeString(collectionViewUrl);
        dest.writeString(feedUrl);
        dest.writeString(trackViewUrl);
        dest.writeString(artworkUrl30);
        dest.writeString(artworkUrl60);
        dest.writeString(artworkUrl100);
        if (collectionPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(collectionPrice);
        }
        if (trackPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(trackPrice);
        }
        if (trackRentalPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(trackRentalPrice);
        }
        if (collectionHdPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(collectionHdPrice);
        }
        if (trackHdPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(trackHdPrice);
        }
        if (trackHdRentalPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(trackHdRentalPrice);
        }
        dest.writeString(releaseDate);
        dest.writeString(collectionExplicitness);
        dest.writeString(trackExplicitness);
        if (trackCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(trackCount);
        }
        dest.writeString(country);
        dest.writeString(currency);
        dest.writeString(primaryGenreName);
        dest.writeString(contentAdvisoryRating);
        dest.writeString(artworkUrl600);
        if (genreIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genreIds);
        }
        if (genres == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genres);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SinglePodcast> CREATOR = new Parcelable.Creator<SinglePodcast>() {
        @Override
        public SinglePodcast createFromParcel(Parcel in) {
            return new SinglePodcast(in);
        }

        @Override
        public SinglePodcast[] newArray(int size) {
            return new SinglePodcast[size];
        }
    };
}