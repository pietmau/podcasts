package com.pietrantuono.podcasts.addpodcast.model;


import java.util.List;

import rx.Observer;

public interface AddPodcastsModel {
    void subscribeToSearch(Observer<List<SearchPodcastItem>> observer);

    void unsubscribeFromSearch();

    void searchPodcasts(String query);
}