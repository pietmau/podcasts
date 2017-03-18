package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
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
    private boolean isSubscribed;

    public SinglePodcastModelImpl(SinglePodcastApi singlePodcastApi, Repository repository) {
        this.singlePodcastApi = singlePodcastApi;
        this.repository = repository;
    }

    @Override
    public void subscribeToFeed(Observer<PodcastFeed> obspodcastFeedObserverrver) {
        Subscription subscription = podcastFeedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(mainThread()).cache().subscribe(obspodcastFeedObserverrver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribeToIsSubscribedToPodcast(Observer<Boolean> isSubscribedObserver) {
        Subscription subscription = isSubscribedObservable.cache().subscribe(isSubscribedObserver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void setSubscribedToPodcast(boolean isSubscribed) {
        this.isSubscribed=isSubscribed;
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
    public void getIsSubscribedToPodcast(Integer trackId) {
        isSubscribedObservable = repository.getIfSubscribed(trackId);
    }

    @Override
    public boolean isSubscribedToPodcasat() {
        return isSubscribed;
    }

    @Override
    public void actuallySubscribesToPodcast(SinglePodcast singlePodcast) {

    }
}
