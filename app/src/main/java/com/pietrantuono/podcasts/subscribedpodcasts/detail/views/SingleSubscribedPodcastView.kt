package com.pietrantuono.podcasts.subscribedpodcasts.detail.views

import com.pietrantuono.podcasts.apis.PodcastEpisode

interface SingleSubscribedPodcastView {
    fun setEpisodes(episodes: List<PodcastEpisode>?)
    fun enterWithTransition()
    fun enterWithoutTransition()
    fun setTitle(collectionName: String?)

}