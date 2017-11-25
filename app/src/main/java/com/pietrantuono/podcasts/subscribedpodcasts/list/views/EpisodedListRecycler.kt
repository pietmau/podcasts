package com.pietrantuono.podcasts.subscribedpodcasts.list.views

import android.content.Context
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.ImageView
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.subscribedpodcasts.list.di.SingleSubscribedModule
import pojos.Episode
import javax.inject.Inject

class EpisodedListRecycler : RecyclerView {
    @Inject lateinit var adapter: EpisodesListAdapter
    private var onItemClickListener: OnItemClickListener? = null

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
        adapter!!.setItems(episodes)
    }

    private fun init() {
        (context.applicationContext as App).applicationComponent!!
                .with(SingleSubscribedModule(context as AppCompatActivity))
                .inject(this)
        layoutManager = createLayoutManager()
        setAdapter(adapter)
    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return GridLayoutManager(context, 2)
        } else {
            return LinearLayoutManager(context)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
        adapter!!.onItemClickListener = listener
    }

    fun isPartiallyHidden(position: Int): Boolean {
        return (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() < position
    }

    interface OnItemClickListener {
        fun onItemClicked(episode: Episode, image: ImageView, position: Int)
    }
}
