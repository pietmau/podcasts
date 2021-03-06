package com.pietrantuono.podcasts.playlist.view.custom

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.EpisodeViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.databinding.PlaylistItemBinding
import models.pojos.Episode

class PlaylistHolder(
        itemView: View,
        private val resourcesProvider: ResourcesProvider)
    : RecyclerView.ViewHolder(itemView) {

    private val dataBinding: PlaylistItemBinding

    init {
        dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView) as PlaylistItemBinding
    }

    fun bind(episode: Episode, onItemClickListener: ((Episode) -> Unit)?, isCurrentlyPlaying: Boolean) {
        val podcastEpisodeViewModel = EpisodeViewModel(episode, resourcesProvider)
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
        dataBinding.root.setOnClickListener {
            onItemClickListener?.invoke(episode)
        }
        if (isCurrentlyPlaying) {
            dataBinding.root.setBackgroundColor(resourcesProvider.getColor(R.color.colorAccent))
        }
    }

}