package com.pietrantuono.podcasts.addpodcast.singlepodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

import rx.Observable;
import rx.Observer;

public interface Repository {
    Observable<Boolean> getIfSubscribed(SinglePodcast trackId);

    Observable<List<SinglePodcast>> subscribeToSubscribedPodcasts(Observer<List<SinglePodcast>> observer);

    void onSubscribeUnsubscribeToPodcastClicked(SinglePodcast podcast);
    }
