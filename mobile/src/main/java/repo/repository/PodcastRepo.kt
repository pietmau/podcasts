package repo.repository

import models.pojos.Episode
import models.pojos.Podcast
import models.pojos.PodcastRealm
import rx.Observable


interface PodcastRepo {
    fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast>
    fun getSubscribedPodcasts(): Observable<List<Podcast>>
    fun getIfSubscribed(podcast: Podcast?): Observable<Boolean>
    fun subscribeUnsubscribeToPodcast(podcast: Podcast?)
    fun getPodcastByEpisodeSync(episode: Episode): Podcast?
    fun savePodcastSync(podcast: PodcastRealm?)
    fun getPodcastByIdSync(trackId: Int): Podcast?
    fun getSubscribedPodcastsAsObservable(): Observable<List<Podcast>>
    fun savePodcastAndEpisodesAsync(podcast: Podcast, episodes: List<Episode>)
}
