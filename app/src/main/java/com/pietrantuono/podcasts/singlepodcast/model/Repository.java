package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import rx.Observable;

public interface Repository {
    Observable<Boolean> getIfSubscribed(Integer trackId);

    void actuallySubscribesToPodcast(SinglePodcast singlePodcast);
}
