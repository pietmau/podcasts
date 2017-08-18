package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.customviews.EpisodesListHolder
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.PodcastEpisode

import java.util.ArrayList

class EpisodesListAdapter(private val resourcesProvider: ResourcesProvider) : RecyclerView.Adapter<EpisodesListHolder>() {
    private val items: MutableList<PodcastEpisode>
    var onItemClickListener: EpisodedListRecycler.OnItemClickListener? = null

    init {
        items = ArrayList<PodcastEpisode>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesListHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.episode_item, parent, false)
        return EpisodesListHolder(view, resourcesProvider)
    }

    override fun onBindViewHolder(holder: EpisodesListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<PodcastEpisode>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
