package com.pietrantuono.podcasts.singlepodcast.model;

import rx.Observable;
import rx.Observer;

public interface Repository {
    Observable<Boolean> getIfSubscribed(Integer trackId);
}
