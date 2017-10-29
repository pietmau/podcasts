package com.pietrantuono.podcasts.repository.repository

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.providers.PodcastRealm
import com.pietrantuono.podcasts.providers.RealmUtlis
import io.realm.Realm
import rx.Observable
import rx.subjects.BehaviorSubject

class PodcastRepoRealm(
        private val reposServices: RepoServices,
        private val logger: DebugLogger
) : PodcastRepo {

    private val TAG = "PodcastRepoRealm"

    /** To be used from another Thread or from a service in another process . */
    override fun savePodcastSync(podcast: PodcastRealm) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.copyToRealmOrUpdate(podcast)
            }
        }
    }

    override fun getPodcastByEpisodeSync(episode: Episode): Podcast? {
        return Realm.getDefaultInstance().use {
            var result = it?.where(PodcastRealm::class.java)?.
                    equalTo("episodes.link", episode.link)?.
                    findFirst()
            result = it.copyFromRealm(result)
            result
        }
    }

    private var subject: BehaviorSubject<Boolean>? = null

    override fun subscribeUnsubscribeToPodcast(podcast: Podcast?) {
        var singlePodcast: PodcastRealm? = null
        Realm.getDefaultInstance().executeTransactionAsync(object : Realm.Transaction {
            override fun execute(realm: Realm?) {
                singlePodcast = realm?.
                        where(PodcastRealm::class.java)?.
                        equalTo("trackId", podcast?.trackId)?.
                        findFirst() ?: RealmUtlis.toSinglePodcastRealm(podcast)
                singlePodcast!!.isPodcastSubscribed = !singlePodcast!!.isPodcastSubscribed
                singlePodcast = realm?.copyToRealmOrUpdate(singlePodcast)
                singlePodcast = realm?.copyFromRealm(singlePodcast)
            }
        }, object : Realm.Transaction.OnSuccess {
            override fun onSuccess() {
                reposServices.getAndDowloadEpisodes(singlePodcast, singlePodcast?.isPodcastSubscribed)
                subject?.onNext(singlePodcast?.isPodcastSubscribed)
            }
        })
    }

    override fun getIfSubscribed(podcast: Podcast?): Observable<Boolean> {
        subject = BehaviorSubject.create<Boolean>()
        Realm.getDefaultInstance().use { realm ->
            realm.where(PodcastRealm::class.java)
                    .equalTo("trackId", podcast?.trackId)
                    .findFirstAsync()
                    .asObservable<PodcastRealm>()
                    .filter { it.isLoaded && it.isValid }
                    .map { it.isPodcastSubscribed }
                    .subscribe(subject)
        }
        return subject!!.asObservable()
    }

    override fun getSubscribedPodcasts(): Observable<List<Podcast>> {
        return Realm.getDefaultInstance().use { realm ->
            realm.where(PodcastRealm::class.java)
                    .equalTo("podcastSubscribed", true)
                    .findAllAsync()
                    .asObservable()
                    .doOnNext { logger.debug(TAG, Thread.currentThread().name) }
                    //.filter { it.isLoaded && it.isValid }
                    .map { realm.copyFromRealm(it) }
                    .map { it as List<Podcast> }
                    .cache()
        }
    }

    override fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast> {
        return Realm.getDefaultInstance().use { realm ->
            realm.where(PodcastRealm::class.java)
                    .equalTo("trackId", trackId)
                    .findFirstAsync()
                    .asObservable<PodcastRealm>()
                    .filter { it.isLoaded && it.isValid }
                    .map(realm::copyFromRealm)
                    .cache()
        }
    }
}

