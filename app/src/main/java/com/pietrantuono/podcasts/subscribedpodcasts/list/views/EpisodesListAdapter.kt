package com.pietrantuono.podcasts.subscribedpodcasts.list.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.customviews.EpisodesListHolder
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import models.pojos.Episode
import java.util.*

class EpisodesListAdapter(private val resourcesProvider: ResourcesProvider) : RecyclerView.Adapter<EpisodesListHolder>() {
    private val items: MutableList<Episode>
    var onItemClickListener: EpisodedListRecycler.OnItemClickListener? = null

    init {
        items = ArrayList<Episode>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesListHolder {
        return EpisodesListHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.episode_item, parent, false), resourcesProvider)
    }

    override fun onBindViewHolder(holder: EpisodesListHolder, position: Int) {
        holder.bind(items[position], onItemClickListener, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Episode>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
