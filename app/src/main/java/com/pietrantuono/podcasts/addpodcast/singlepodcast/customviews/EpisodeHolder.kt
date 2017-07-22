package com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.PodcastEpisodeViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel

class EpisodeHolder(itemView: View, private val resourcesProvider: ResourcesProvider) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: ViewDataBinding

    init {
        dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    }

    fun bind(podcastEpisodeModel: PodcastEpisodeModel) {
        val podcastEpisodeViewModel = PodcastEpisodeViewModel(podcastEpisodeModel, resourcesProvider)
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
    }
}
