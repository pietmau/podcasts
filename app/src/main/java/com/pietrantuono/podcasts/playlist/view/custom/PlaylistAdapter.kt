package com.pietrantuono.podcasts.playlist.view.custom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import models.pojos.Episode

class PlaylistAdapter(private val resources: ResourcesProvider) : RecyclerView.Adapter<PlaylistHolder>() {
    private val playlist = mutableListOf<Episode>()

    override fun onBindViewHolder(holder: PlaylistHolder?, position: Int) {
        //holder.bind(items[position], onItemClickListener, onDownloadImageClickListener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.playlist_item, parent, false)
        return PlaylistHolder(itemView, resources)
    }

    override fun getItemCount(): Int = playlist.size

    fun addEpisode(episode: Episode) {
        playlist.add(episode)
        notifyItemInserted(playlist.size - 1)
    }


}