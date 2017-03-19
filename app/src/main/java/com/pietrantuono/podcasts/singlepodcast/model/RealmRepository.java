package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.providers.SinglePodcastRealm;
import com.pietrantuono.podcasts.providers.SinglePodcastRealmFactory;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class RealmRepository implements Repository {
    Realm realm = Realm.getDefaultInstance();
    int count;

    @Override
    public Observable<Boolean> getIfSubscribed(Integer trackId) {

        Observable<Boolean> observable = realm
                .where(SinglePodcastRealm.class)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable()
                .map(x -> isSubscribed(x))
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private boolean isSubscribed(RealmObject x) {
        if (!x.isLoaded()) {
            return false;
        }
        if (!x.isValid()) {
            return false;
        }
        return ((SinglePodcastRealm)x).isPodcastSubscribed();
    }

    @Override
    public void actuallySubscribesToPodcast(SinglePodcast singlePodcast) {
        realm.beginTransaction();
        SinglePodcastRealm singlePodcastRealm = new SinglePodcastRealmFactory().singlePodcastRealm(singlePodcast);
        singlePodcastRealm.setPodcastSubscribed(true);
        realm.copyToRealm(singlePodcastRealm);
        realm.commitTransaction();
    }
}
