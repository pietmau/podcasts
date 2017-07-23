package com.pietrantuono.podcasts.addpodcast.singlepodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import rx.Observer;

public interface SinglePodcastModel {

    void subscribeToFeed(Observer<PodcastFeed> observer);

    void unsubscribe();

    void subscribeToIsSubscribedToPodcast(Observer<Boolean> observer);

    void startModel(SinglePodcast podcast);

    void onSubscribeUnsubscribeToPodcastClicked();
}