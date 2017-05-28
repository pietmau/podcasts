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
import rx.subjects.BehaviorSubject;

public class RealmRepository implements Repository {
    Realm realm = Realm.getDefaultInstance();
    private BehaviorSubject<Boolean> subject;

    @Override
    public Observable<Boolean> getIfSubscribed(SinglePodcast podcast) {
        Observable<Boolean> observable = realm
                .where(SinglePodcastRealm.class)
                .equalTo("trackId", podcast.getTrackId())
                .findFirstAsync()
                .asObservable()
                .map(x -> isSubscribed(x));
        subject = BehaviorSubject.create();
        observable.subscribe(subject);
        return subject.asObservable().observeOn(AndroidSchedulers.mainThread());
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
    public Observable<List<SinglePodcast>> subscribeToSubscribedPodcasts(Observer<List<SinglePodcast>> observer) {
        return realm
                .where(SinglePodcastRealm.class)
                .findAllAsync()
                .asObservable()
                .map(x -> toSinglePodcast(x))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onSubscribeUnsubscribeToPodcastClicked(SinglePodcast podcast) {
        realm.executeTransactionAsync(realm -> {
            SinglePodcastRealm singlePodcastRealm = getSinglePodcastRealm(podcast, realm);
            if (singlePodcastRealm != null) {
                singlePodcastRealm.setPodcastSubscribed(!singlePodcastRealm.isPodcastSubscribed());
            } else {
                singlePodcastRealm = RealmUtlis.singlePodcastRealm(podcast);
                singlePodcastRealm.setPodcastSubscribed(!singlePodcastRealm.isPodcastSubscribed());
                realm.copyToRealm(singlePodcastRealm);
            }
            subject.onNext(singlePodcastRealm.isPodcastSubscribed());
        });
    }

    private List<SinglePodcast> toSinglePodcast(RealmResults<SinglePodcastRealm> results) {
        List<SinglePodcast> list = new ArrayList<>(results.size());
        for (SinglePodcastRealm single : results) {
            list.add(RealmUtlis.singlePodcast(single));
        }
        return list;
    }

    private SinglePodcastRealm getSinglePodcastRealm(SinglePodcast singlePodcast, Realm realm) {
        return realm.where(SinglePodcastRealm.class)
                .equalTo("trackId", singlePodcast.getTrackId())
                .findFirst();
    }
}
