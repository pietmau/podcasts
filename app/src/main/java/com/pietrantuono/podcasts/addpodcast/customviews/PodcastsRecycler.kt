package com.pietrantuono.podcasts.addpodcast.customviews


import android.content.Context
import android.content.res.Configuration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet


import com.pietrantuono.podcasts.application.App
import models.pojos.Podcast

import javax.inject.Inject

class PodcastsRecycler(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    @Inject lateinit var adapter: PodcastsAdapter

    init {
        (context.applicationContext as App).appComponent?.inject(this)
        layoutManager = createLayoutManager()
        setAdapter(adapter)
    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return GridLayoutManager(context, 4)
        } else {
            return GridLayoutManager(context, 3)
        }
    }

    fun setItems(items: List<Podcast>) {
        adapter.setItems(items)
    }

    private fun setOnItemClickListener(onItemClickedClickedListener: PodcastsAdapter.OnItemClickedClickedListener) {
        adapter.setOnItemClickListener(onItemClickedClickedListener)
    }

    fun setListeners(addPodcastPresenter: PodcastsAdapter.OnItemClickedClickedListener) {
        setOnItemClickListener(addPodcastPresenter)
    }

    fun isPartiallyHidden(position: Int): Boolean {
        val isPartiallyHiddenBottom = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() < position
        val isPartiallyHiddenTop = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() > position
        return isPartiallyHiddenBottom || isPartiallyHiddenTop
    }
}
