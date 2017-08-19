package com.pietrantuono.podcasts.imageloader


import android.widget.ImageView

import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.imageaware.ImageAware
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast

class SimpleImageLoader {

    fun displayImage(url: String?, imageView: ImageView, podcastImageLoadingListener: ImageLoadingListener) {
        ImageLoader.getInstance().displayImage(url, imageView, options, podcastImageLoadingListener)
    }

    fun displayImage(url: String?, imageView: ImageView) {
        ImageLoader.getInstance().displayImage(url, imageView, options)
    }

    fun displayImage(url: String?, imageViewAware: ImageAware) {
        ImageLoader.getInstance().displayImage(url, imageViewAware, options)
    }

    fun displayImage(podcast: Podcast?, imageView: ImageView, podcastImageLoadingListener: ImageLoadingListener) {
        if (podcast == null) {
            return
        }
        val url = podcast.artworkUrl600
        displayImage(url, imageView, podcastImageLoadingListener)
    }

    fun loadImage(url: String?) {
        ImageLoader.getInstance().loadImage(url, null)
    }

    fun loadImage(url: String?, listener: ImageLoadingListener) {
        ImageLoader.getInstance().loadImage(url, listener)
    }

    companion object {
        private val options = DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.podcast_grey_icon_very_big)
                .showImageOnFail(R.drawable.podcast_grey_icon_very_big)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build()
    }
}
