package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.providers.RealmUtlis;
import com.pietrantuono.podcasts.providers.SinglePodcastRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class RealmRepository implements Repository {
    Realm realm = Realm.getDefaultInstance();


    @Override
    public Observable<Boolean> getIfSubscribed(Integer trackId) {
        return realm
                .where(SinglePodcastRealm.class)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable()
                .map(x -> isSubscribed(x))
                .observeOn(AndroidSchedulers.mainThread());
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
    public void actuallySubscribesToPodcast(SinglePodcast singlePodcast) {
        realm.executeTransactionAsync(realm1 -> {
            SinglePodcastRealm singlePodcastRealm = getSinglePodcastRealm(singlePodcast, realm1);
            if (singlePodcastRealm != null) {
                singlePodcastRealm.setPodcastSubscribed(true);
            } else {
                singlePodcastRealm = RealmUtlis.singlePodcastRealm(singlePodcast);
                singlePodcastRealm.setPodcastSubscribed(true);
                realm1.copyToRealm(singlePodcastRealm);
            }
        });
    }

    @Override
    public void actuallyUnSubscribesToPodcast(SinglePodcast singlePodcast) {
        realm.executeTransactionAsync(realm1 -> {
            SinglePodcastRealm singlePodcastRealm = getSinglePodcastRealm(singlePodcast, realm1);
            if (singlePodcastRealm != null) {
                singlePodcastRealm.setPodcastSubscribed(false);
            }
        });
    }

    @Override
    public Observable<List<SinglePodcast>> subscribeToSubscribedPodcasts(Observer<List<SinglePodcast>> observer) {
        return realm
                .where(SinglePodcastRealm.class)
                .findAllAsync()
                .asObservable()
                .map(x -> toSinglePodcast(x))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<SinglePodcast> toSinglePodcast(RealmResults<SinglePodcastRealm> results) {
        List<SinglePodcast> list = new ArrayList<>(results.size());
        for (SinglePodcastRealm single : results) {
            list.add(RealmUtlis.singlePodcast(single));
        }
        return list;
    }


    private SinglePodcastRealm getSinglePodcastRealm(SinglePodcast singlePodcast, Realm realm1) {
        return realm1.where(SinglePodcastRealm.class)
                .equalTo("trackId", singlePodcast.getTrackId())
                .findFirst();
    }
}
