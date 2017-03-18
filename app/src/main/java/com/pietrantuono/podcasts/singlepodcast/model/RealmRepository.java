package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.providers.SinglePodcastRealm;

import io.realm.Realm;
import rx.Observable;

public class RealmRepository implements Repository {

    @Override
    public Observable<Boolean> getIfSubscribed(Integer trackId) {
        Realm realm = Realm.getDefaultInstance();
        Observable<Boolean> observable = realm
                .where(SinglePodcastRealm.class)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable()
                .map(x -> x != null);
        realm.close();
        return observable;
    }
}
