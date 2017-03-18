package com.pietrantuono.podcasts.singlepodcast.model;


import rx.Observable;

public class RealmRepository implements Repository {

    @Override
    public Observable<Boolean> getIfSubscribed(Integer trackId) {
        return null;
    }
}
