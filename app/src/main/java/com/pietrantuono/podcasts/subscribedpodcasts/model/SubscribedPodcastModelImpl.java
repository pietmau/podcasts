package com.pietrantuono.podcasts.subscribedpodcasts.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.addpodcast.repository.repository.Repository;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class SubscribedPodcastModelImpl implements SubscribedPodcastModel {
    private final Repository repository;
    private Subscription subscription;

    public SubscribedPodcastModelImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeToSubscribedPodcasts(Observer<List<Podcast>> observer) {
        Observable<List<Podcast>> observable = repository.getSubscribedPodcasts(observer);
        subscription = observable.subscribe(observer);
    }
}
