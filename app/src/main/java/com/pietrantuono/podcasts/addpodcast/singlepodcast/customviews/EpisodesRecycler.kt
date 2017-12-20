package com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews

import android.content.Context
import android.content.res.Configuration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.pietrantuono.podcasts.application.App
import models.pojos.Episode
import javax.inject.Inject

class EpisodesRecycler : RecyclerView {
    @Inject lateinit var adapter: EpisodesAdapter

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun setItems(episodes: List<Episode>) {
        adapter.setItems(episodes)
    }

    private fun init() {
        (context.applicationContext as App).applicationComponent?.inject(this)
        layoutManager = createLayoutManager()
        setAdapter(adapter)
    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
    }

    internal fun setOnItemClickListener(clickListener: EpisodesAdapter.OnItemClickListener) {
        adapter.setOnItemClickListener(clickListener)
    }
}
