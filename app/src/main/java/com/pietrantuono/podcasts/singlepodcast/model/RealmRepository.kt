package com.pietrantuono.podcasts.singlepodcast.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.providers.RealmUtlis
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject
import java.util.*

class RealmRepository : Repository {
    private val realm = Realm.getDefaultInstance()
    private var subject: BehaviorSubject<Boolean>? = null

    override fun getIfSubscribed(podcast: SinglePodcast): Observable<Boolean> {
        val observable = realm
                .where(SinglePodcastRealm::class.java)
                .equalTo("trackId", podcast.trackId)
                .findFirstAsync()
                .asObservable<RealmObject>()
                .map { x -> isSubscribed(x) }
        subject = BehaviorSubject.create<Boolean>()
        observable.subscribe(subject)
        return subject!!.asObservable().observeOn(AndroidSchedulers.mainThread())
    }

    private fun isSubscribed(x: RealmObject): Boolean {
        if (!x.isLoaded) {
            return false
        }
        if (!x.isValid) {
            return false
        }
        return (x as SinglePodcastRealm).isPodcastSubscribed
    }

    override fun subscribeToSubscribedPodcasts(observer: Observer<List<SinglePodcast>>): Observable<List<SinglePodcast>> {
        return realm
                .where(SinglePodcastRealm::class.java)
                .findAllAsync()
                .asObservable()
                .map { x -> toSinglePodcast(x) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onSubscribeUnsubscribeToPodcastClicked(podcast: SinglePodcast) {
        realm.executeTransactionAsync { realm ->
            var singlePodcastRealm: SinglePodcastRealm? = getSinglePodcastRealm(podcast, realm)
            if (singlePodcastRealm != null) {
                singlePodcastRealm.isPodcastSubscribed = !singlePodcastRealm.isPodcastSubscribed
            } else {
                singlePodcastRealm = RealmUtlis.singlePodcastRealm(podcast)
                singlePodcastRealm!!.isPodcastSubscribed = !singlePodcastRealm.isPodcastSubscribed
                realm.copyToRealm(singlePodcastRealm)
            }
            subject!!.onNext(singlePodcastRealm.isPodcastSubscribed)
        }
    }

    private fun toSinglePodcast(results: RealmResults<SinglePodcastRealm>): List<SinglePodcast> {
        val list = ArrayList<SinglePodcast>(results.size)
        for (single in results) {
            list.add(RealmUtlis.singlePodcast(single))
        }
        return list
    }

    private fun getSinglePodcastRealm(singlePodcast: SinglePodcast, realm: Realm): SinglePodcastRealm? {
        return realm.where(SinglePodcastRealm::class.java)
                .equalTo("trackId", singlePodcast.trackId)
                .findFirst()
    }
}
