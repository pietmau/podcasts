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
    private Observable<Boolean> isSubscribedToPodcastObservable;
    private SinglePodcast podcast;

    public SinglePodcastModelImpl(SinglePodcastApi singlePodcastApi, Repository repository) {
        this.singlePodcastApi = singlePodcastApi;
        this.repository = repository;
    }

    @Override
    public void init(SinglePodcast podcast) {
        this.podcast=podcast;
        if (podcast != null) {
            getFeed(podcast.getFeedUrl());
            getIsSubscribedToPodcast(podcast.getTrackId());
        }
    }

    @Override
    public void actuallyUnSubscribesToPodcast() {
        repository.actuallyUnSubscribesToPodcast(podcast);
    }

    @Override
    public void subscribeToFeed(Observer<PodcastFeed> obspodcastFeedObserverrver) {
        Subscription subscription = podcastFeedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(mainThread()).cache().subscribe(obspodcastFeedObserverrver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribeToIsSubscribedToPodcast(Observer<Boolean> isSubscribedObserver) {
        Subscription subscription = isSubscribedToPodcastObservable.subscribe(isSubscribedObserver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void isSubscribedToPodcast(Observer<Boolean> isSubscribedObserver) {
        Subscription subscription = isSubscribedToPodcastObservable.take(1).subscribe(isSubscribedObserver);
        compositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.unsubscribe();
    }

    private void getFeed(String url) {
        podcastFeedObservable = singlePodcastApi.getFeed(url);
    }


    private void getIsSubscribedToPodcast(Integer trackId) {
        isSubscribedToPodcastObservable = repository.getIfSubscribed(trackId);
    }

    @Override
    public void actuallySubscribesToPodcast() {
        repository.actuallySubscribesToPodcast(podcast);
    }


}
