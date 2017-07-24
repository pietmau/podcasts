package com.pietrantuono.podcasts.addpodcast.singlepodcast.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.providers.RealmUtlis
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.subjects.BehaviorSubject

class RealmRepository(private val realm: Realm) : Repository, Observer<List<SinglePodcast>> {
    private var subscription: Subscription? = null

    override fun onCompleted() {}

    override fun onNext(t: List<SinglePodcast>?) {}

    override fun onError(e: Throwable?) {}

    override fun unsubscribe() {
        if (subscription != null && !(subscription!!.isUnsubscribed)) {
            subscription?.unsubscribe()
        }
    }

    private var subject: BehaviorSubject<Boolean>? = null

    override fun getIfSubscribed(podcast: SinglePodcast?): Observable<Boolean> {
        subject = BehaviorSubject.create<Boolean>()
        realm.where(SinglePodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirstAsync()
                .asObservable<SinglePodcastRealm>()
                .filter { it.isLoaded && it.isValid }
                .map { it.isPodcastSubscribed }.subscribe(subject)
        return subject!!.asObservable()
    }


    override fun subscribeToSubscribedPodcasts(observer: Observer<List<SinglePodcast>>?): Observable<List<SinglePodcast>> {
        var connectableObservable = realm.where(SinglePodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAllAsync()
                .asObservable()
                .filter { it.isLoaded && it.isValid }
                .map { realm.copyFromRealm(it) }
                .map { it as List<SinglePodcast> }
                .share()
                .replay()
        subscription = connectableObservable?.subscribe(this)
        return connectableObservable!!.asObservable()
    }

    override fun onSubscribeUnsubscribeToPodcastClicked(podcast: SinglePodcast?) {
        var singlePodcast = realm.where(SinglePodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirst()
        if (singlePodcast == null) {
            singlePodcast = RealmUtlis.singlePodcastRealm(podcast)
        }
        realm.executeTransaction {
            singlePodcast.isPodcastSubscribed = !singlePodcast.isPodcastSubscribed
            realm.copyToRealmOrUpdate(singlePodcast)
        }
        subject?.onNext(singlePodcast.isPodcastSubscribed)
    }

}
