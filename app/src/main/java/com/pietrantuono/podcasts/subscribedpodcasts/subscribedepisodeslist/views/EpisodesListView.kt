package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views

import android.annotation.TargetApi
import android.support.v7.widget.CardView
import android.widget.ImageView
import com.pietrantuono.podcasts.apis.Episode

interface EpisodesListView {
    fun setEpisodes(episodes: List<Episode>)
    fun enterWithTransition()
    fun enterWithoutTransition()
    fun setTitle(collectionName: String?)
    @TargetApi(value = 21) fun startDetailActivityWithTransition(episode: Episode, imageView: ImageView, cardView: CardView)
    @TargetApi(value = 21) fun startDetailActivityWithoutTransition(episode: Episode)
    fun isPartiallyHidden(position: Int): Boolean
}