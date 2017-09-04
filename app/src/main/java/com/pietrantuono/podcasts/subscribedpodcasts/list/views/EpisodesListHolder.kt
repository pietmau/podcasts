package com.pietrantuono.podcasts.addpodcast.customviews

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.EpisodeViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.databinding.EpisodeItemBinding
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodedListRecycler


class EpisodesListHolder(itemView: View, private val resourcesProvider: ResourcesProvider) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: EpisodeItemBinding

    init {
        dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView) as EpisodeItemBinding
    }

    fun bind(episode: Episode, onItemClickListener: EpisodedListRecycler.OnItemClickListener?, position: Int) {
        val podcastEpisodeViewModel = EpisodeViewModel(episode, resourcesProvider)
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
        dataBinding.root.setOnClickListener {
            onItemClickListener?.let { it.onItemClicked(episode, dataBinding.imageLayout!!.singlePodcastImage, position) }
        }
    }
}


