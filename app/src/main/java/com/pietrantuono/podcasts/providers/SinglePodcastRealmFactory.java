package com.pietrantuono.podcasts.providers;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

public class SinglePodcastRealmFactory {

    public SinglePodcastRealm singlePodcastRealm(SinglePodcast podcast) {
        SinglePodcastRealm singlePodcastRealm = new SinglePodcastRealm();
        singlePodcastRealm.setArtistName(podcast.getArtistName());
        singlePodcastRealm.setArtworkUrl30(podcast.getArtworkUrl30());
        singlePodcastRealm.setArtworkUrl60(podcast.getArtworkUrl60());
        singlePodcastRealm.setArtworkUrl100(podcast.getArtworkUrl100());
        singlePodcastRealm.setArtworkUrl600(podcast.getArtworkUrl600());
        singlePodcastRealm.setCollectionCensoredName(podcast.getCollectionCensoredName());
        singlePodcastRealm.setCollectionExplicitness(podcast.getCollectionExplicitness());
        singlePodcastRealm.setCollectionHdPrice(podcast.getCollectionHdPrice());
        singlePodcastRealm.setCollectionId(podcast.getCollectionId());
        singlePodcastRealm.setCollectionName(podcast.getCollectionName());
        singlePodcastRealm.setCollectionPrice(podcast.getCollectionPrice());
        singlePodcastRealm.setCollectionViewUrl(podcast.getCollectionViewUrl());
        singlePodcastRealm.setContentAdvisoryRating(podcast.getContentAdvisoryRating());
        singlePodcastRealm.setCountry(podcast.getCountry());
        singlePodcastRealm.setCurrency(podcast.getCurrency());
        singlePodcastRealm.setFeedUrl(podcast.getFeedUrl());
        singlePodcastRealm.setGenreIds(podcast.getGenreIds());
        singlePodcastRealm.setGenres(podcast.getGenres());
        singlePodcastRealm.setKind(podcast.getKind());
        singlePodcastRealm.setPrimaryGenreName(podcast.getPrimaryGenreName());
        singlePodcastRealm.setReleaseDate(podcast.getReleaseDate());
        singlePodcastRealm.setTrackCensoredName(podcast.getTrackCensoredName());
        singlePodcastRealm.setTrackCount(podcast.getTrackCount());
        singlePodcastRealm.setTrackExplicitness(podcast.getTrackExplicitness());
        singlePodcastRealm.setTrackHdPrice(podcast.getTrackHdPrice());
        singlePodcastRealm.setTrackHdRentalPrice(podcast.getTrackHdRentalPrice());
        singlePodcastRealm.setTrackId(podcast.getTrackId());
        singlePodcastRealm.setTrackPrice(podcast.getTrackPrice());
        singlePodcastRealm.setTrackName(singlePodcastRealm.getTrackName());
        singlePodcastRealm.setTrackHdPrice(podcast.getTrackHdPrice());
        singlePodcastRealm.setTrackViewUrl(podcast.getTrackViewUrl());
        singlePodcastRealm.setTrackRentalPrice(podcast.getTrackRentalPrice());
        singlePodcastRealm.setWrapperType(podcast.getWrapperType());
        return singlePodcastRealm;
    }




















}
