package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class SinglePodcastModelImpl implements SinglePodcastModel {
    private final SinglePodcastApi singlePodcastApi;
    private final Repository repository;
    private Observable<PodcastFeed> podcastFeedObservable;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Observable<Boolean> isSubscribedObservable;
    private final PodcastSchedulers podcastSchedulers;

    public SinglePodcastModelImpl(SinglePodcastApi singlePodcastApi, Repository repository,
                                  PodcastSchedulers podcastSchedulers) {
        this.singlePodcastApi = singlePodcastApi;
        this.repository = repository;
        this.podcastSchedulers = podcastSchedulers;
    }

    @Override
    public void subscribeToFeed(Observer<PodcastFeed> obspodcastFeedObserverrver) {
        Subscription subscription = podcastFeedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(mainThread()).cache().subscribe(obspodcastFeedObserverrver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribeToIsSubscribed(Observer<Boolean> isSubscribedObserver) {
        Subscription subscription = isSubscribedObservable.subscribeOn(podcastSchedulers.newThread())
                .observeOn(podcastSchedulers.mainThread()).cache().subscribe(isSubscribedObserver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public void getFeed(String url) {
        podcastFeedObservable = singlePodcastApi.getFeed(url);
    }

    @Override
    public void getIsSubscribed(Integer trackId) {
        isSubscribedObservable = repository.getIfSubscribed(trackId);
    }

}
