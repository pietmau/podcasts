package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

import rx.Observable;
import rx.Observer;

public interface Repository {
    Observable<Boolean> getIfSubscribed(Integer trackId);

    void actuallySubscribesToPodcast(SinglePodcast singlePodcast);

    void actuallyUnSubscribesToPodcast(SinglePodcast singlePodcast);

    Observable<List<SinglePodcast>> subscribeToSubscribedPodcasts(Observer<List<SinglePodcast>> observer);
}
