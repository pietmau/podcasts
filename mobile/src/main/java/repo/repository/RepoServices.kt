package repo.repository

import pojos.Podcast

interface RepoServices {
    fun getAndDowloadEpisodes(podcast: Podcast?, podcastSubscribed: Boolean?)
}