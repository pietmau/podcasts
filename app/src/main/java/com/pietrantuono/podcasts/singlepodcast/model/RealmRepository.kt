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

    override fun getIfSubscribed(podcast: SinglePodcast?): Observable<Boolean> {
        val observable = realm
                .where(SinglePodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirstAsync()
                .asObservable<RealmObject>()
                .map { realmObject -> isSubscribed(realmObject) }
        subject = BehaviorSubject.create<Boolean>()
        observable.subscribe(subject)
        return subject!!.asObservable().observeOn(AndroidSchedulers.mainThread())
    }

    private fun isSubscribed(realmObject: RealmObject): Boolean {
        if (!realmObject.isLoaded || !realmObject.isValid) {
            return false
        }

        return (realmObject as SinglePodcastRealm).isPodcastSubscribed
    }

    override fun subscribeToSubscribedPodcasts(observer: Observer<List<SinglePodcast>>?): Observable<List<SinglePodcast>> {
        return realm
                .where(SinglePodcastRealm::class.java)
                .findAllAsync()
                .asObservable()
                .map { realmResults -> toSinglePodcast(realmResults) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onSubscribeUnsubscribeToPodcastClicked(podcast: SinglePodcast?) {
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
        if (!results.isLoaded || !results.isValid) {
            return list
        }
        for (single in results) {
            list.add(RealmUtlis.singlePodcast(single))
        }
        return list
    }

    private fun getSinglePodcastRealm(singlePodcast: SinglePodcast?, realm: Realm): SinglePodcastRealm? {
        return realm.where(SinglePodcastRealm::class.java)
                .equalTo("trackId", singlePodcast?.trackId)
                .findFirst()
    }
}
