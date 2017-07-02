package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.podcasts.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class SinglePodcastModelImpl implements SinglePodcastModel {
    private final SinglePodcastApi singlePodcastApi;
    private final Repository repository;
    private Observable<PodcastFeed> podcastFeedObservable;
    private SinglePodcast podcast;
    private Subscription feed;
    private Subscription isSubscribedToPodcast;

    public SinglePodcastModelImpl(SinglePodcastApi singlePodcastApi, Repository repository) {
        this.singlePodcastApi = singlePodcastApi;
        this.repository = repository;
    }

    @Override
    public void startModel(SinglePodcast podcast) {
        this.podcast = podcast;
        if (podcast != null) {
            getFeed(podcast.getFeedUrl());
        }
    }

    @Override
    public void onSubscribeUnsubscribeToPodcastClicked() {
        repository.onSubscribeUnsubscribeToPodcastClicked(podcast);
    }

    @Override
    public void subscribeToFeed(Observer<PodcastFeed> obspodcastFeedObserverrver) {
        feed = podcastFeedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(mainThread()).cache().subscribe(obspodcastFeedObserverrver);
    }

    @Override
    public void subscribeToIsSubscribedToPodcast(Observer<Boolean> isSubscribedObserver) {
        isSubscribedToPodcast = getIsSubscribedToPodcast().subscribe(isSubscribedObserver);
    }

    @Override
    public void unsubscribe() {
        if (feed != null) {
            feed.unsubscribe();
        }
        if(isSubscribedToPodcast!=null){
            isSubscribedToPodcast.unsubscribe();
        }
    }

    private void getFeed(String url) {
        podcastFeedObservable = singlePodcastApi.getFeed(url);
    }

    private Observable<Boolean> getIsSubscribedToPodcast() {
        return repository.getIfSubscribed(podcast);
    }
}
