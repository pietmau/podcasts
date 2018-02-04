package com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import models.pojos.Episode
import java.util.*

class EpisodesAdapter(private val resourcesProvider: ResourcesProvider) : RecyclerView.Adapter<EpisodeHolder>() {
    private val items: MutableList<Episode>
    private var clickListener: OnItemClickListener? = null

    init {
        items = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.episode_item, parent, false)
        return EpisodeHolder(view, resourcesProvider)
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Episode>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }


    interface OnItemClickListener {
        fun onItemClick(episode: Episode)
    }
}
