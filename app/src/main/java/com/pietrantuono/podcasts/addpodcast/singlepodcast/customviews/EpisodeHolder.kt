package com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.EpisodeViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import pojos.Episode


class EpisodeHolder(itemView: View, private val resourcesProvider: ResourcesProvider) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: ViewDataBinding

    init {
        dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    }

    fun bind(episode: Episode) {
        val podcastEpisodeViewModel = EpisodeViewModel(episode, resourcesProvider)
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
    }
}
