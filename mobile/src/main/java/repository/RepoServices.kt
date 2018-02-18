package repository

import models.pojos.Podcast

interface RepoServices {
    fun getAndDowloadEpisodes(podcast: Podcast?, podcastSubscribed: Boolean?)
}