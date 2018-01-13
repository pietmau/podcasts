package com.pietrantuono.podcasts.playlist.view.custom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import hugo.weaving.DebugLog
import models.pojos.Episode

class PlaylistAdapter(
        private val resources: ResourcesProvider,
        private val callback: ((Episode) -> Unit)?)
    : RecyclerView.Adapter<PlaylistHolder>() {

    private val playlist = mutableListOf<Episode>()
    private var currentlyPlayingPosition = -1

    var currentlyPlayingMediaId: String? = null
        @DebugLog
        set(uri) {
            if (uri.equals(field, true)) {
                return
            }
            hilightCurrentlyPlaying(uri)
            field = uri
        }

    @DebugLog
    private fun hilightCurrentlyPlaying(uri: String?) {
        val oldPosition = currentlyPlayingPosition
        currentlyPlayingPosition = getPositionForUri(uri)
        notifyCurrentlyPlayingItemChanged(currentlyPlayingPosition)
        notifyCurrentlyPlayingItemChanged(oldPosition)
    }

    @DebugLog
    private fun notifyCurrentlyPlayingItemChanged(position: Int) {
        if (position >= 0) {
            notifyItemChanged(position)
        }
    }

    @DebugLog
    private fun getPositionForUri(uri: String?): Int {
        uri ?: return -1
        for (index in playlist.indices) {
            if (playlist[index].uri.equals(uri, true)) {
                return index
            }
        }
        return -1
    }

    @DebugLog
    override fun onBindViewHolder(holder: PlaylistHolder?, position: Int) {
        holder?.bind(playlist[position], callback, position == currentlyPlayingPosition)
    }

    @DebugLog
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.playlist_item, parent, false)
        return PlaylistHolder(itemView, resources)
    }

    @DebugLog
    override fun getItemCount(): Int = playlist.size

    @DebugLog
    fun addEpisode(episode: Episode) {
        playlist.add(episode)
        notifyItemInserted(playlist.size - 1)
        hilightCurrentlyPlaying(currentlyPlayingMediaId)
    }
}