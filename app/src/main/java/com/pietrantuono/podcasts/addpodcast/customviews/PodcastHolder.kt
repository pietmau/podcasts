package com.pietrantuono.podcasts.addpodcast.customviews

import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.OnClick
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.BR
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.databinding.FindPodcastItemBinding
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider

class PodcastHolder(itemView: View, private val resourcesProvider: ResourcesProvider, private val loader: SimpleImageLoader) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: FindPodcastItemBinding
    private val popupMenu: SimplePopUpMenu? = null

    init {
        dataBinding = DataBindingUtil.bind<FindPodcastItemBinding>(itemView)
    }

    fun onBindViewHolder(singlePodcast: SinglePodcast, onSunscribeClickedListener: PodcastsAdapter.OnSunscribeClickedListener, onItemClickedClickedListener: PodcastsAdapter.OnItemClickedClickedListener, position: Int) {
        val podcastEpisodeViewModel = SinlglePodcastViewModel(singlePodcast, resourcesProvider, onItemClickedClickedListener, onSunscribeClickedListener, position)
        dataBinding.setVariable(BR.sinlglePodcastViewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
        dataBinding.titleContainer.setBackgroundColor(resourcesProvider.getColor(R.color.colorPrimary))
        setUpOnClickListener(singlePodcast, position, onItemClickedClickedListener)
        loadImage()
    }

    private fun loadImage() {
        loader.displayImage(dataBinding.sinlglePodcastViewModel.imageUrl, dataBinding.podcastImage, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                val drawable = (view as ImageView).drawable as BitmapDrawable
                Palette.from(drawable.bitmap).generate { palette ->
                    val vibrant = palette.vibrantSwatch
                    if (vibrant != null) {
                        dataBinding.titleContainer.setBackgroundColor(vibrant.rgb)
                    } else {
                        dataBinding.titleContainer.setBackgroundColor(resourcesProvider.getColor(R.color.colorPrimary))
                    }
                }
            }
        })
    }

    private fun setUpOnClickListener(singlePodcast: SinglePodcast, position: Int, onItemClickedClickedListener: PodcastsAdapter.OnItemClickedClickedListener) {
        dataBinding.podcastImage.setOnClickListener { view: View -> onItemClickedClickedListener.onItemClicked(singlePodcast, dataBinding.podcastImage, position, dataBinding.titleContainer) }
    }

    @OnClick(R.id.overflow)
    fun showMenu() {
        popupMenu!!.show()
    }


}
