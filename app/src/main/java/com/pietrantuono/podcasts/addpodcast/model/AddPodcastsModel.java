package com.pietrantuono.podcasts.addpodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.List;

import rx.Observer;

public interface AddPodcastsModel {
    void subscribeToSearch(Observer<List<PodcastSearchResult>> observer);

    void unsubscribeFromSearch();

    void searchPodcasts(String query);

}
