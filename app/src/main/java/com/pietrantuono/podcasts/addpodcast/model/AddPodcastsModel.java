package com.pietrantuono.podcasts.addpodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

import rx.Observer;

public interface AddPodcastsModel {
    void subscribeToSearch(Observer<List<SinglePodcast>> observer);

    void unsubscribeFromSearch();

    void searchPodcasts(String query);

}
