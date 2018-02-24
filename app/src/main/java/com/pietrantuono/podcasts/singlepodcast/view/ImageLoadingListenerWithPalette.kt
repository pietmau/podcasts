package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.databinding.FindPodcastItemBinding

class ImageLoadingListenerWithPalette(private val dataBinding: FindPodcastItemBinding?,
                                      private val resourcesProvider: ResourcesProvider) : SimpleImageLoadingListener() {

    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
        val drawable = (view as ImageView).drawable as BitmapDrawable
        Palette.from(drawable.bitmap).generate { palette ->
            val vibrant = palette.vibrantSwatch
            if (vibrant != null) {
                dataBinding?.titleContainer?.setBackgroundColor(vibrant.rgb)
            } else {
                dataBinding?.titleContainer?.setBackgroundColor(resourcesProvider.getColor(R.color.colorPrimary))
            }
        }
    }
}


