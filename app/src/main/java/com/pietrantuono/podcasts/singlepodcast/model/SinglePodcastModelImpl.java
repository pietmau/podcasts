package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.pietrantuono.podcasts.apis.SinglePodcastApi;

import rx.Observer;

public class SinglePodcastModelImpl implements SinglePodcastModel {
    private SinglePodcastApi singlePodcastApi;

    public SinglePodcastModelImpl(SinglePodcastApi singlePodcastApi) {
        this.singlePodcastApi = singlePodcastApi;
    }

    @Override
    public void subscribe(Observer<PodcastFeed> observer) {

    }

    @Override
    public void unsubscribe() {

    }
}
