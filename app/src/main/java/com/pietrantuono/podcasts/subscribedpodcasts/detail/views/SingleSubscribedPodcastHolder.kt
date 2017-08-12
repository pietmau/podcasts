package com.pietrantuono.podcasts.addpodcast.customviews

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.PodcastEpisodeView
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.databinding.EpisodeItemBinding


class SingleSubscribedPodcastHolder(itemView: View, private val resourcesProvider: ResourcesProvider) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: EpisodeItemBinding

    init {
        dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView) as EpisodeItemBinding
    }

    fun bind(podcastEpisode: PodcastEpisode) {
        val podcastEpisodeViewModel = PodcastEpisodeView(podcastEpisode, resourcesProvider)
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
    }
}


