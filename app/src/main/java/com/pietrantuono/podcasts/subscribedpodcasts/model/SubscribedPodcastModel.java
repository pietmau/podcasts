package com.pietrantuono.podcasts.subscribedpodcasts.model;

import rx.Observer;

public interface SubscribedPodcastModel {
    void subscribeToSubscribedPodcasts(Observer observer);
}
