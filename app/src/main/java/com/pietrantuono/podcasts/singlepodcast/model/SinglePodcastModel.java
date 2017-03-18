package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import rx.Observer;

public interface SinglePodcastModel {

    void subscribeToFeed(Observer<PodcastFeed> observer);

    void unsubscribe();

    void getFeed(String url);

    void getIsSubscribedToPodcast(Integer trackId);

    void subscribeToIsSubscribedToPodcast(Observer<Boolean> observer);

    void setSubscribedToPodcast(boolean isSubscribed);

    boolean isSubscribedToPodcasat();

    void actuallySubscribesToPodcast(SinglePodcast singlePodcast);
}
