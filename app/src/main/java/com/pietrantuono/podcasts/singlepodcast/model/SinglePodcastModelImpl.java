package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.pietrantuono.podcasts.apis.SinglePodcastApi;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SinglePodcastModelImpl implements SinglePodcastModel {
    private SinglePodcastApi singlePodcastApi;
    private Observer<PodcastFeed> observer;
    private Observable<PodcastFeed> observable;
    private Subscription subscription;

    public SinglePodcastModelImpl(SinglePodcastApi singlePodcastApi) {
        this.singlePodcastApi = singlePodcastApi;
    }

    @Override
    public void subscribe(Observer<PodcastFeed> observer) {
        this.observer = observer;
        subscribe();
    }

    @Override
    public void unsubscribe() {
        subscription.unsubscribe();
    }

    @Override
    public void getFeed(String url) {
        observable = singlePodcastApi.getFeed(url);
    }

    private void subscribe() {
        subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache().subscribe(observer);
    }
}
