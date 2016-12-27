package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.apis.PodcastFeed;

import rx.Observer;

public interface SinglePodcastModel {

    void subscribe(Observer<PodcastFeed> observer);

    void unsubscribe();

    void getFeed(String url);
}
