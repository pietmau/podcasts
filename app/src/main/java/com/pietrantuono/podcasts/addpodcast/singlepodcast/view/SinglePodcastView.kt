package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import models.pojos.Episode

interface SinglePodcastView {

    var title: String?

    fun enterWithTransition()

    fun enterWithoutTransition()

    fun showProgress(show: Boolean)

    fun setEpisodes(episodes: List<Episode>)

    fun exitWithSharedTrsnsition()

    fun exitWithoutSharedTransition()

    fun setSubscribedToPodcast(isSubscribed: Boolean?)

    fun onError(string: String?)

    fun enablePullToRefresh(enable: Boolean)
}
