package com.pietrantuono.podcasts.subscribedpodcasts.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.Repository;

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
    public void subscribeToSubscribedPodcasts(Observer<List<SinglePodcast>> observer) {
        Observable<List<SinglePodcast>> observable = repository.subscribeToSubscribedPodcasts(observer);
        subscription = observable.subscribe(observer);
    }

    @Override
    public void unsubscribe() {
        if(subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        repository.unsubscribe();
    }
}
