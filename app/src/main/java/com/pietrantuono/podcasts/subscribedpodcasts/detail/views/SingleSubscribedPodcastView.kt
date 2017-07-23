package com.pietrantuono.podcasts.subscribedpodcasts.detail.views

import com.pietrantuono.podcasts.providers.RealmPodcastEpisodeModel

interface SingleSubscribedPodcastView {
    fun setEpisodes(episodes: List<RealmPodcastEpisodeModel>?)
    fun enterWithTransition()
    fun enterWithoutTransition()
    fun setTitle(collectionName: String?)

}