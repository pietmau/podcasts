package com.pietrantuono.podcasts.playlist.view.custom

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import models.pojos.Episode


class PlaylistRecycler(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = PlaylistAdapter()
    }

    fun addEpisode(episode: Episode) {
        (adapter as PlaylistAdapter).addEpisode(episode)
    }

}
