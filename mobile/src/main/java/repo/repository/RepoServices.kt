package repo.repository

import diocan.pojos.Podcast

interface RepoServices {
    fun getAndDowloadEpisodes(podcast: Podcast?, podcastSubscribed: Boolean?)
}