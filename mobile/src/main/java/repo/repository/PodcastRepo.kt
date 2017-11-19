package repo.repository

import pojos.Episode
import pojos.Podcast
import pojos.PodcastRealm
import rx.Observable


interface PodcastRepo {
    fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast>
    fun getSubscribedPodcasts(): Observable<List<Podcast>>
    fun getIfSubscribed(podcast: Podcast?): Observable<Boolean>
    fun subscribeUnsubscribeToPodcast(podcast: Podcast?)
    fun getPodcastByEpisodeSync(episode: Episode): Podcast?
    fun savePodcastSync(podcast: PodcastRealm)
}
