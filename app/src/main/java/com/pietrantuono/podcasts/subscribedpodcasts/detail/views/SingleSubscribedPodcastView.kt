package com.pietrantuono.podcasts.subscribedpodcasts.detail.views

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel

interface SingleSubscribedPodcastView {
    fun setEpisodes(episodes: List<PodcastEpisodeModel>?)
    fun enterWithTransition()
    fun enterWithoutTransition()
    fun setTitle(collectionName: String?)

}