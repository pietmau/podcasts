package com.pietrantuono.podcasts.subscribedpodcasts.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;

import java.util.List;

import rx.Observer;

public interface SubscribedPodcastModel {
    void subscribeToSubscribedPodcasts(Observer<List<Podcast>> observer);
}
