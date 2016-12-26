package com.pietrantuono.podcasts.addpodcast.model;


import rx.Observer;

public interface AddPodcastsModel {
    void subscribeToSearch(Observer<SearchResult> observer);

    void unsubscribeFromSearch();

    void searchPodcasts(String query);

}
