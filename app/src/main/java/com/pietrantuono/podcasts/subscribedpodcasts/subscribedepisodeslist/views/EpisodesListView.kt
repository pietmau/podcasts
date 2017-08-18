package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views

import android.annotation.TargetApi
import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.PodcastEpisode

interface EpisodesListView {
    fun setEpisodes(episodes: List<PodcastEpisode>)
    fun enterWithTransition()
    fun enterWithoutTransition()
    fun setTitle(collectionName: String?)
    @TargetApi(value = 21) fun startDetailActivityWithTransition(podcast: Podcast?, imageView: ImageView?, titleContainer: LinearLayout?)
    @TargetApi(value = 21) fun startDetailActivityWithoutTransition(podcast: Podcast?)
    fun isPartiallyHidden(position: Int): Boolean
}