package repo.repository

import diocan.pojos.Episode
import diocan.pojos.Podcast
import diocan.pojos.PodcastRealm
import rx.Observable


interface PodcastRepo {
    fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast>
    fun getSubscribedPodcasts(): Observable<List<Podcast>>
    fun getIfSubscribed(podcast: Podcast?): Observable<Boolean>
    fun subscribeUnsubscribeToPodcast(podcast: Podcast?)
    fun getPodcastByEpisodeSync(episode: Episode): Podcast?
    fun savePodcastSync(podcast: PodcastRealm)
}
