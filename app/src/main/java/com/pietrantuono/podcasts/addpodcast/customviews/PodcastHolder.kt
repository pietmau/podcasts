package com.pietrantuono.podcasts.addpodcast.customviews

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter.OnItemClickedClickedListener

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ImageLoadingListenerWithPalette
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.databinding.FindPodcastItemBinding
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import models.pojos.Podcast

class PodcastHolder(itemView: View, private val resources: ResourcesProvider,
                    private val loader: SimpleImageLoader) : RecyclerView.ViewHolder(itemView) {
    private val binding: FindPodcastItemBinding?

    init {
        binding = DataBindingUtil.bind<FindPodcastItemBinding>(itemView)
    }

    fun onBindViewHolder(podcast: Podcast, onItemClickedClickedListener:
    OnItemClickedClickedListener, position: Int) {
        val viewModel = SinlglePodcastViewModel(podcast, resources)
        binding?.setVariable(BR.sinlglePodcastViewModel, viewModel)
        binding?.executePendingBindings()
        binding?.titleContainer?.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        setUpClickListener(podcast, position, onItemClickedClickedListener)
        loadImage()
    }

    private fun loadImage() {
        val listener = ImageLoadingListenerWithPalette(binding, resources)
        loader.displayImage(binding?.sinlglePodcastViewModel?.imageUrl, binding?.podcastImage, listener)
    }

    private fun setUpClickListener(podcast: Podcast?, position: Int, listener: OnItemClickedClickedListener) {
        binding?.podcastImage?.setOnClickListener {
            listener.onItemClicked(podcast, binding.podcastImage, position, binding.titleContainer)
        }
    }

}
