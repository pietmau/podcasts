package com.pietrantuono.podcasts.subscribedpodcasts.list.views

import android.view.MenuInflater
import pojos.Episode

interface EpisodesListView {
    var title: String?
    fun setEpisodes(episodes: List<Episode>)
    fun enterWithTransition()
    fun enterWithoutTransition()
    fun startDetailActivityWithoutTransition(episode: Episode)
    fun isPartiallyHidden(position: Int): Boolean
    fun getMenuInflater(): MenuInflater
    fun exitWithoutSharedTransition()
    fun exitWithSharedTrsnsition()
}