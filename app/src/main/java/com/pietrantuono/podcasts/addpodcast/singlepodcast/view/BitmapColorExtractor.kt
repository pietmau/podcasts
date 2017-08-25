package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView

import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class BitmapColorExtractor(private val transitionsFramework: TransitionsFramework)
    : SimpleImageLoadingListener() {
    var callback: Callback? = null
    var backgroundColor: Int? = null

    override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
        callback?.onColorExtractionCompleted()
    }

    override fun onLoadingCancelled(imageUri: String?, view: View?) {
        callback?.onColorExtractionCompleted()
    }

    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
        Palette.from(((view as ImageView).drawable as BitmapDrawable).bitmap).generate {
            backgroundColor = it?.vibrantSwatch?.rgb
            callback?.onColorExtractionCompleted()
        }
    }

    interface Callback {
        fun onColorExtractionCompleted()
    }
}

