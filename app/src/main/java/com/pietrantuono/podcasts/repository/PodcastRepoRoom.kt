package com.pietrantuono.podcasts.repository

import android.arch.persistence.room.Dao
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import rx.Observable

class PodcastRepoRoom(private val dao: PodcastDao) : PodcastRepo {

    override fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast> {
        return Observable.empty()
    }

    override fun getSubscribedPodcasts(): Observable<List<Podcast>> {
        return Observable.empty()
    }

    override fun getIfSubscribed(podcast: Podcast?): Observable<Boolean> {
        return Observable.empty()
    }

    override fun subscribeUnsubscribeToPodcast(podcast: Podcast?) {
        podcast?.trackId?.let { dao.findBytrackId(it) }
    }

    override fun getPodcastByEpisodeSync(episode: Episode): Podcast? {
        return null
    }

    override fun savePodcastSync(podcast: Podcast) {

    }

}

@Dao
interface PodcastDao {
    fun findBytrackId(trackId: Int): Any

}
