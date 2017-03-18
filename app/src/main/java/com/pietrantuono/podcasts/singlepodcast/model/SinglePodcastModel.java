package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.apis.PodcastFeed;

import rx.Observer;

public interface SinglePodcastModel {

    void subscribeToFeed(Observer<PodcastFeed> observer);

    void unsubscribe();

    void getFeed(String url);

    void getIsSubscribed(Integer trackId);

    void subscribeToIsSubscribed(Observer<Boolean> observer);
}
