package com.pietrantuono.podcasts.subscribedpodcasts.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

import rx.Observer;

public interface SubscribedPodcastModel {
    void subscribeToSubscribedPodcasts(Observer<List<SinglePodcast>> observer);
}
