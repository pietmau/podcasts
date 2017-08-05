package com.pietrantuono.podcasts.repository.repository

import android.util.Log
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.providers.PodcastRealm
import com.pietrantuono.podcasts.providers.RealmUtlis
import io.realm.Realm
import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject

class PodcastRepoRealm(private val realm: Realm, private val reposServices: RepoServices) : PodcastRepo {

    companion object {
        val TAG: String? = "PodcastRepoRealm"
    }

    private var subject: BehaviorSubject<Boolean>? = null

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
        if (singlePodcast != null) {
            Log.d(TAG, "singlePodcast != null")
            reposServices.subscribeUnsubscribeToPodcast(singlePodcast)
            subject?.onNext(!singlePodcast.isPodcastSubscribed)
        }
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

    override fun getSubscribedPodcasts(observer: Observer<List<Podcast>>): Observable<List<Podcast>> {
        return realm.where(PodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAllAsync()
                .asObservable()
                .filter { it.isLoaded && it.isValid }
                .map { realm.copyFromRealm(it) }
                .map { it as List<Podcast> }
                .cache()
    }

    override fun getPodcastById(trackId: Int): Observable<out Podcast> {
        return realm.where(PodcastRealm::class.java)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable<PodcastRealm>()
                .filter(PodcastRealm::isLoaded)
                .map(realm::copyFromRealm)
                .cache()
    }
}

