package com.pietrantuono.podcasts.addpodcast.repository.repository


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.providers.PodcastRealm
import com.pietrantuono.podcasts.providers.RealmUtlis
import io.realm.Realm
import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject

class RealmRepository(private val realm: Realm, private val podcastsRepo: PodcastRepo) : Repository {
    private var subject: BehaviorSubject<Boolean>? = null

    override fun getPodcastById(trackId: Int): Observable<out Podcast> {

        return realm.where(PodcastRealm::class.java)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable<PodcastRealm>()
                .filter(PodcastRealm::isLoaded)
                .map(realm::copyFromRealm)
                .cache()
    }

    override fun getIfSubscribed(podcast: Podcast?): Observable<Boolean> {
        subject = BehaviorSubject.create<Boolean>()
        realm.where(PodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirstAsync()
                .asObservable<PodcastRealm>()
                .filter { it.isLoaded && it.isValid }
                .map { it.isPodcastSubscribed }
                .subscribe(subject)
        return subject!!.asObservable()
    }

    override fun getSubscribedPodcasts(observer: Observer<List<Podcast>>?): Observable<List<Podcast>> {
        return realm.where(PodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAllAsync()
                .asObservable()
                .filter { it.isLoaded && it.isValid }
                .map { realm.copyFromRealm(it) }
                .map { it as List<Podcast> }
                .cache()
    }

    override fun subscribeUnsubscribeToPodcast(podcast: Podcast?) {
        var singlePodcast = realm.where(PodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirst()
        if (singlePodcast == null) {
            singlePodcast = RealmUtlis.toSinglePodcastRealm(podcast)
        }
        realm.executeTransactionAsync {
            singlePodcast.isPodcastSubscribed = !singlePodcast.isPodcastSubscribed
            it.copyToRealmOrUpdate(singlePodcast)
        }
        subject?.onNext(singlePodcast.isPodcastSubscribed)
    }
}
