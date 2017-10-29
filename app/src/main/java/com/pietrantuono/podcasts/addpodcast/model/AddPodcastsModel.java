package com.pietrantuono.podcasts.addpodcast.model;


import io.reactivex.observers.DisposableObserver;

public interface AddPodcastsModel {
    void subscribeToSearch(DisposableObserver<SearchResult> observer);

    void unsubscribeFromSearch();

    void searchPodcasts(String query);

}
