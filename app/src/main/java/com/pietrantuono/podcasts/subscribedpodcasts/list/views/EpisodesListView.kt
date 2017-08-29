package com.pietrantuono.podcasts.subscribedpodcasts.list.views

import android.annotation.TargetApi
import android.view.MenuInflater
import android.widget.ImageView
import com.pietrantuono.podcasts.apis.Episode

interface EpisodesListView {
    var title: String?
    fun setEpisodes(episodes: List<Episode>)
    fun enterWithTransition()
    fun enterWithoutTransition()
    @TargetApi(value = 21) fun startDetailActivityWithTransition(episode: Episode, imageView: ImageView)
    @TargetApi(value = 21) fun startDetailActivityWithoutTransition(episode: Episode)
    fun isPartiallyHidden(position: Int): Boolean
    fun getMenuInflater(): MenuInflater
}