package com.pietrantuono.podcasts.addpodcast.repository.repository


import io.realm.Realm

class RealmRepository(private val realm: Realm, private val podcastsRepo: PodcastRepo)
    : Repository, PodcastRepo by podcastsRepo {

}
