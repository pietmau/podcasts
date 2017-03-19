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
        return ((SinglePodcastRealm) x).isPodcastSubscribed();
    }

    @Override
    public void actuallySubscribesToPodcast(SinglePodcast singlePodcast) {//TODO async
        realm.executeTransactionAsync(realm1 -> {
            SinglePodcastRealm singlePodcastRealm = getSinglePodcastRealm(singlePodcast, realm1);
            if (singlePodcastRealm != null) {
                singlePodcastRealm.setPodcastSubscribed(true);
            } else {
                singlePodcastRealm = new SinglePodcastRealmFactory().singlePodcastRealm(singlePodcast);
                singlePodcastRealm.setPodcastSubscribed(true);
                realm1.copyToRealm(singlePodcastRealm);
            }
        });
    }


    @Override
    public void actuallyUnSubscribesToPodcast(SinglePodcast singlePodcast) { //TODO async
        realm.executeTransactionAsync(realm1 -> {
            SinglePodcastRealm singlePodcastRealm = getSinglePodcastRealm(singlePodcast, realm1);
            if (singlePodcastRealm != null) {
                singlePodcastRealm.setPodcastSubscribed(false);
            }
        });
    }

    private SinglePodcastRealm getSinglePodcastRealm(SinglePodcast singlePodcast, Realm realm1) {
        return realm1.where(SinglePodcastRealm.class)
                .equalTo("trackId", singlePodcast.getTrackId())
                .findFirst();
    }
}
