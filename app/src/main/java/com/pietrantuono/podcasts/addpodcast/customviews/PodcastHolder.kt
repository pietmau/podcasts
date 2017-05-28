package com.pietrantuono.podcasts.addpodcast.customviews

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter.OnItemClickedClickedListener
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.databinding.FindPodcastItemBinding
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.singlepodcast.view.ImageLoadingListenerWithPalette
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider

class PodcastHolder(itemView: View, private val resources: ResourcesProvider, private val loader: SimpleImageLoader) : RecyclerView.ViewHolder(itemView) {
    private val binding: FindPodcastItemBinding

    init {
        binding = DataBindingUtil.bind<FindPodcastItemBinding>(itemView)
    }

    fun onBindViewHolder(singlePodcast: SinglePodcast, onItemClickedClickedListener: OnItemClickedClickedListener, position: Int) {
        val viewModel = SinlglePodcastViewModel(singlePodcast, resources)
        binding.setVariable(BR.sinlglePodcastViewModel, viewModel)
        binding.executePendingBindings()
        binding.titleContainer.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        setUpClickListener(singlePodcast, position, onItemClickedClickedListener)
        loadImage()
    }

    private fun loadImage() {
        val listener = ImageLoadingListenerWithPalette(binding, resources)
        loader.displayImage(binding.sinlglePodcastViewModel.imageUrl, binding.podcastImage, listener)
    }

    private fun setUpClickListener(podcast: SinglePodcast, position: Int, listener: OnItemClickedClickedListener) {
        binding.podcastImage.setOnClickListener { view: View -> listener.onItemClicked(podcast, binding.podcastImage, position, binding.titleContainer) }
    }

}
