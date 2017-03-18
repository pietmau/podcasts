package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.providers.SinglePodcastRealm;
import com.pietrantuono.podcasts.providers.SinglePodcastRealmFactory;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class RealmRepository implements Repository {
    Realm realm = Realm.getDefaultInstance();

    @Override
    public Observable<Boolean> getIfSubscribed(Integer trackId) {

        Observable<Boolean> observable = realm
                .where(SinglePodcastRealm.class)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable()
                .map(x -> x.isValid())
                .observeOn(AndroidSchedulers.mainThread());

        SinglePodcastRealm zz = realm.where(SinglePodcastRealm.class)
                .equalTo("trackId", trackId)
                .findFirst();

        return observable;
    }

    @Override
    public void actuallySubscribesToPodcast(SinglePodcast singlePodcast) {
        realm.beginTransaction();
        SinglePodcastRealm singlePodcastRealm = new SinglePodcastRealmFactory().singlePodcastRealm(singlePodcast);
        realm.copyToRealm(singlePodcastRealm);
        realm.commitTransaction();
    }
}
