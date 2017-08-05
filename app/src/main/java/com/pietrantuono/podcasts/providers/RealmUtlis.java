package com.pietrantuono.podcasts.providers;


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleEnclosure;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class RealmUtlis {

    public static List<String> toStringList(RealmList<RealmString> realmList) {
        List<String> list = new ArrayList<>(realmList.size());
        for (RealmString s : realmList) {
            list.add(s.getString());
        }
        return list;
    }

    public static RealmList<RealmString> toRealmStringList(List<String> list) {
        RealmList<RealmString> realmStrings = new RealmList<RealmString>();
        for (String s : list) {
            realmStrings.add(new RealmString(s));
        }
        return realmStrings;
    }

    public static PodcastRealm toSinglePodcastRealm(Podcast podcast) {
        PodcastRealm singlePodcastRealm = new PodcastRealm();
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
        singlePodcastRealm.setTrackName(podcast.getTrackName());
        singlePodcastRealm.setTrackHdPrice(podcast.getTrackHdPrice());
        singlePodcastRealm.setTrackViewUrl(podcast.getTrackViewUrl());
        singlePodcastRealm.setTrackRentalPrice(podcast.getTrackRentalPrice());
        singlePodcastRealm.setWrapperType(podcast.getWrapperType());
        singlePodcastRealm.setEpisodes(podcast.getEpisodes());
        return singlePodcastRealm;
    }

    private static RealmList<SimpleEnclosure> getEnclosures(PodcastEpisode podcastEpisode) {
        RealmList<SimpleEnclosure> simpleEnclosures = new RealmList<>();
        for (SyndEnclosure syndEnclosure : podcastEpisode.getEnclosures()) {
            simpleEnclosures.add(new SimpleEnclosure(syndEnclosure));
        }
        return simpleEnclosures;
    }
}
