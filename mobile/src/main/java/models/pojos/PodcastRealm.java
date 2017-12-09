package models.pojos;


import android.os.Parcel;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import models.utils.RealmUtlis;

public class PodcastRealm extends RealmObject implements Podcast {
    private String wrapperType;
    private String kind;
    private Integer collectionId;
    @PrimaryKey
    private Integer trackId;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String collectionCensoredName;
    private String trackCensoredName;
    private String collectionViewUrl;
    private String feedUrl;
    private String trackViewUrl;
    private String artworkUrl30;
    private String artworkUrl60;
    private String artworkUrl100;
    private Double collectionPrice;
    private Double trackPrice;
    private Integer trackRentalPrice;
    private Integer collectionHdPrice;
    private Integer trackHdPrice;
    private Integer trackHdRentalPrice;
    private String releaseDate;
    private String collectionExplicitness;
    private String trackExplicitness;
    private Integer trackCount;
    private String country;
    private String currency;
    private String primaryGenreName;
    private String contentAdvisoryRating;
    private String artworkUrl600;
    private RealmList<RealmString> genreIds = null;
    private RealmList<RealmString> genres = null;
    private RealmList<RealmEpisode> episodes = new RealmList<>();
    private boolean podcastSubscribed;
    private Integer numberOfEpisodesDowloaded;

    public PodcastRealm() {
    }

    /**
     * @return The wrapperType
     */
    @Override
    public String getWrapperType() {
        return wrapperType;
    }

    /**
     * @param wrapperType The wrapperType
     */
    @Override
    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    /**
     * @return The kind
     */
    @Override
    public String getKind() {
        return kind;
    }

    /**
     * @param kind The kind
     */
    @Override
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return The collectionId
     */
    @Override
    public Integer getCollectionId() {
        return collectionId;
    }

    /**
     * @param collectionId The collectionId
     */
    @Override
    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * @return The trackId
     */
    @Override
    public Integer getTrackId() {
        return trackId;
    }

    /**
     * @param trackId The trackId
     */
    @Override
    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    /**
     * @return The artistName
     */
    @Override
    public String getArtistName() {
        return artistName;
    }

    /**
     * @param artistName The artistName
     */
    @Override
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * @return The collectionName
     */
    @Override
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * @param collectionName The collectionName
     */
    @Override
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * @return The trackName
     */
    @Override
    public String getTrackName() {
        return trackName;
    }

    /**
     * @param trackName The trackName
     */
    @Override
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /**
     * @return The collectionCensoredName
     */
    @Override
    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    /**
     * @param collectionCensoredName The collectionCensoredName
     */
    @Override
    public void setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
    }

    /**
     * @return The trackCensoredName
     */
    @Override
    public String getTrackCensoredName() {
        return trackCensoredName;
    }

    /**
     * @param trackCensoredName The trackCensoredName
     */
    @Override
    public void setTrackCensoredName(String trackCensoredName) {
        this.trackCensoredName = trackCensoredName;
    }

    /**
     * @return The collectionViewUrl
     */
    @Override
    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    /**
     * @param collectionViewUrl The collectionViewUrl
     */
    @Override
    public void setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
    }

    /**
     * @return The feedUrl
     */
    @Override
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     * @param feedUrl The feedUrl
     */
    @Override
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    /**
     * @return The trackViewUrl
     */
    @Override
    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    /**
     * @param trackViewUrl The trackViewUrl
     */
    @Override
    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    /**
     * @return The artworkUrl30
     */
    @Override
    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    /**
     * @param artworkUrl30 The artworkUrl30
     */
    @Override
    public void setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
    }

    /**
     * @return The artworkUrl60
     */
    @Override
    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    /**
     * @param artworkUrl60 The artworkUrl60
     */
    @Override
    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    /**
     * @return The artworkUrl100
     */
    @Override
    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    /**
     * @param artworkUrl100 The artworkUrl100
     */
    @Override
    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    /**
     * @return The collectionPrice
     */
    @Override
    public Double getCollectionPrice() {
        return collectionPrice;
    }

    /**
     * @param collectionPrice The collectionPrice
     */
    @Override
    public void setCollectionPrice(Double collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    /**
     * @return The trackPrice
     */
    @Override
    public Double getTrackPrice() {
        return trackPrice;
    }

    /**
     * @param trackPrice The trackPrice
     */
    @Override
    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    /**
     * @return The trackRentalPrice
     */
    @Override
    public Integer getTrackRentalPrice() {
        return trackRentalPrice;
    }

    /**
     * @param trackRentalPrice The trackRentalPrice
     */
    @Override
    public void setTrackRentalPrice(Integer trackRentalPrice) {
        this.trackRentalPrice = trackRentalPrice;
    }

    /**
     * @return The collectionHdPrice
     */
    @Override
    public Integer getCollectionHdPrice() {
        return collectionHdPrice;
    }

    /**
     * @param collectionHdPrice The collectionHdPrice
     */
    @Override
    public void setCollectionHdPrice(Integer collectionHdPrice) {
        this.collectionHdPrice = collectionHdPrice;
    }

    /**
     * @return The trackHdPrice
     */
    @Override
    public Integer getTrackHdPrice() {
        return trackHdPrice;
    }

    /**
     * @param trackHdPrice The trackHdPrice
     */
    @Override
    public void setTrackHdPrice(Integer trackHdPrice) {
        this.trackHdPrice = trackHdPrice;
    }

    /**
     * @return The trackHdRentalPrice
     */
    @Override
    public Integer getTrackHdRentalPrice() {
        return trackHdRentalPrice;
    }

    /**
     * @param trackHdRentalPrice The trackHdRentalPrice
     */
    @Override
    public void setTrackHdRentalPrice(Integer trackHdRentalPrice) {
        this.trackHdRentalPrice = trackHdRentalPrice;
    }

    /**
     * @return The releaseDate
     */
    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The releaseDate
     */
    @Override
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return The collectionExplicitness
     */
    @Override
    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    /**
     * @param collectionExplicitness The collectionExplicitness
     */
    @Override
    public void setCollectionExplicitness(String collectionExplicitness) {
        this.collectionExplicitness = collectionExplicitness;
    }

    /**
     * @return The trackExplicitness
     */
    @Override
    public String getTrackExplicitness() {
        return trackExplicitness;
    }

    /**
     * @param trackExplicitness The trackExplicitness
     */
    @Override
    public void setTrackExplicitness(String trackExplicitness) {
        this.trackExplicitness = trackExplicitness;
    }

    /**
     * @return The trackCount
     */
    @Override
    public Integer getTrackCount() {
        return trackCount;
    }

    /**
     * @param trackCount The trackCount
     */
    @Override
    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    /**
     * @return The country
     */
    @Override
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return The primaryGenreName
     */
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    /**
     * @param primaryGenreName The primaryGenreName
     */
    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    /**
     * @return The contentAdvisoryRating
     */
    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    /**
     * @param contentAdvisoryRating The contentAdvisoryRating
     */
    public void setContentAdvisoryRating(String contentAdvisoryRating) {
        this.contentAdvisoryRating = contentAdvisoryRating;
    }

    /**
     * @return The artworkUrl600
     */
    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    /**
     * @param artworkUrl600 The artworkUrl600
     */
    public void setArtworkUrl600(String artworkUrl600) {
        this.artworkUrl600 = artworkUrl600;
    }

    /**
     * @return The genreIds
     */
    public List<String> getGenreIds() {
        return RealmUtlis.toStringList(genreIds);
    }

    /**
     * @param genreIds The genreIds
     */
    public void setGenreIds(List<String> genreIds) {
        this.genreIds = RealmUtlis.toRealmStringList(genreIds);
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return RealmUtlis.toStringList(genres);
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = RealmUtlis.toRealmStringList(genres);
    }

    public void setEpisodes(RealmList<RealmEpisode> episodes) {
        this.episodes = episodes;
    }

    private PodcastRealm(Parcel in) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int describeContents() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new UnsupportedOperationException("Not supported");
    }

    @SuppressWarnings("unused")
    public static final Creator<Podcast> CREATOR = new Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel in) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override
        public Podcast[] newArray(int size) {
            throw new UnsupportedOperationException("Not supported");
        }
    };

    public boolean isPodcastSubscribed() {
        return podcastSubscribed;
    }

    public void setPodcastSubscribed(boolean podcastSubscribed) {
        this.podcastSubscribed = podcastSubscribed;
    }

    @Nullable
    @Override
    public List<Episode> getEpisodes() {
        return new ArrayList<Episode>(episodes);
    }

    @Nullable
    @Override
    public Integer getNumberOfEpisodesDowloaded() {
        return numberOfEpisodesDowloaded;
    }

    @Override
    public void setNumberOfEpisodesDowloaded(@Nullable Integer integer) {
        this.numberOfEpisodesDowloaded = integer;
    }

    @Override
    public void setEpisodes(@Nullable List<Episode> list) {
        if (list == null) {
            return;
        }
        episodes.clear();
        for (Episode episode : list) {
            ((RealmList)episodes).add((RealmEpisode) episode);
        }
    }
}
