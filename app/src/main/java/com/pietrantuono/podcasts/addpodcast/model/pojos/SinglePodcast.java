package com.pietrantuono.podcasts.addpodcast.model.pojos;

import android.os.Parcelable;

public interface SinglePodcast extends Parcelable {
    String getWrapperType();

    void setWrapperType(String wrapperType);

    String getKind();

    void setKind(String kind);

    Integer getCollectionId();

    void setCollectionId(Integer collectionId);

    Integer getTrackId();

    void setTrackId(Integer trackId);

    String getArtistName();

    void setArtistName(String artistName);

    String getCollectionName();

    void setCollectionName(String collectionName);

    String getTrackName();

    void setTrackName(String trackName);

    String getCollectionCensoredName();

    void setCollectionCensoredName(String collectionCensoredName);

    String getTrackCensoredName();

    void setTrackCensoredName(String trackCensoredName);

    String getCollectionViewUrl();

    void setCollectionViewUrl(String collectionViewUrl);

    String getFeedUrl();

    void setFeedUrl(String feedUrl);

    String getTrackViewUrl();

    void setTrackViewUrl(String trackViewUrl);

    String getArtworkUrl30();

    void setArtworkUrl30(String artworkUrl30);

    String getArtworkUrl60();

    void setArtworkUrl60(String artworkUrl60);

    String getArtworkUrl100();

    void setArtworkUrl100(String artworkUrl100);

    Double getCollectionPrice();

    void setCollectionPrice(Double collectionPrice);

    Double getTrackPrice();

    void setTrackPrice(Double trackPrice);

    Integer getTrackRentalPrice();

    void setTrackRentalPrice(Integer trackRentalPrice);

    Integer getCollectionHdPrice();

    void setCollectionHdPrice(Integer collectionHdPrice);

    Integer getTrackHdPrice();

    void setTrackHdPrice(Integer trackHdPrice);

    Integer getTrackHdRentalPrice();

    void setTrackHdRentalPrice(Integer trackHdRentalPrice);

    String getReleaseDate();

    void setReleaseDate(String releaseDate);

    String getCollectionExplicitness();

    void setCollectionExplicitness(String collectionExplicitness);

    String getTrackExplicitness();

    void setTrackExplicitness(String trackExplicitness);

    Integer getTrackCount();

    void setTrackCount(Integer trackCount);

    String getCountry();

    String getPrimaryGenreName();

    String getArtworkUrl600();
}
