package com.pietrantuono.podcasts.playlist.view.custom

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import models.pojos.Episode


class PlaylistRecycler(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = PlaylistAdapter(ResourcesProvider(context))
    }

    fun addEpisode(episode: Episode) {
        (adapter as PlaylistAdapter).addEpisode(episode)
    }

}
