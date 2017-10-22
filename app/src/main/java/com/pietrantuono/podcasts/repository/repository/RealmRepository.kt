package com.pietrantuono.podcasts.repository.repository


class RealmRepository(private val podcastsRepo: PodcastRepo)
    : Repository, PodcastRepo by podcastsRepo {

}
