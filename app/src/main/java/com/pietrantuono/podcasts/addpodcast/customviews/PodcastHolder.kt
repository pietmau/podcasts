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
    private var popupMenu: SimplePopUpMenu? = null
    private var barColor: Int

    init {
        barColor = resourcesProvider.getColor(R.color.colorPrimary)
        dataBinding = DataBindingUtil.bind<FindPodcastItemBinding>(itemView)
    }

    fun onBindViewHolder(singlePodcast: SinglePodcast, onSunscribeClickedListener: PodcastsAdapter.OnSunscribeClickedListener?, onItemClickedClickedListener: PodcastsAdapter.OnItemClickedClickedListener?, position: Int) {
        val podcastEpisodeViewModel = SinlglePodcastViewModel(singlePodcast, resourcesProvider, onItemClickedClickedListener, onSunscribeClickedListener, position)
        dataBinding.setVariable(BR.sinlglePodcastViewModel, podcastEpisodeViewModel)
        dataBinding.executePendingBindings()
        setUpOnClickListener(singlePodcast, position, onItemClickedClickedListener)
        setUpMenu(singlePodcast, onSunscribeClickedListener)
        setOverflowClickListener()
        loadImage()
    }

    private fun loadImage() {
        loader.displayImage(dataBinding.sinlglePodcastViewModel.imageUrl, dataBinding.podcastImage, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                Palette.from(((view as ImageView).drawable as BitmapDrawable).bitmap).generate { palette ->
                    if (palette.vibrantSwatch != null) {
                        barColor = palette.vibrantSwatch!!.rgb
                    }
                    dataBinding.titleContainer.setBackgroundColor(barColor)
                }
            }
        })
    }

    private fun setOverflowClickListener() {
        //((com.pietrantuono.podcasts.databinding.FindPodcastItemBinding) dataBinding).overflow.setOnClickListener(view -> showMenu());
    }

    private fun setUpMenu(singlePodcast: SinglePodcast, onSunscribeClickedListener: PodcastsAdapter.OnSunscribeClickedListener?) {
        //popupMenu = new SimplePopUpMenu(((FindPodcastItemBinding) dataBinding).overflow, singlePodcast, singlePodcast1 -> onSunscribeClickedListener.onSubscribeClicked(singlePodcast1));
    }

    private fun setUpOnClickListener(singlePodcast: SinglePodcast, position: Int, onItemClickedClickedListener: PodcastsAdapter.OnItemClickedClickedListener?) {
        dataBinding.podcastImage.setOnClickListener { view: View -> onItemClickedClickedListener?.onItemClicked(singlePodcast, dataBinding.podcastImage, position, dataBinding.titleContainer) }
    }

    @OnClick(R.id.overflow)
    fun showMenu() {
        popupMenu?.show()
    }
}
