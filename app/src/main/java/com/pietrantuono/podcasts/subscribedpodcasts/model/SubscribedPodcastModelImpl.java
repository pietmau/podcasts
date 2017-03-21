package com.pietrantuono.podcasts.subscribedpodcasts.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.singlepodcast.model.Repository;

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
        subscription = observable.subscribe(new Observer<List<SinglePodcast>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<SinglePodcast> singlePodcasts) {

            }
        });
    }
}
