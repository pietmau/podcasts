package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView

import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import com.pietrantuono.podcasts.utils.isInValidState

class TransitionImageLoadingListener(private val transitionsFramework: TransitionsFramework)
    : SimpleImageLoadingListener() {

    private var activity: AppCompatActivity? = null

    var colors: ExtractedColors? = null//TODO MOVE FROM HERE!

    override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
        onNotLoaded()
    }

    override fun onLoadingCancelled(imageUri: String?, view: View?) {
        onNotLoaded()
    }

    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
        Palette.from(((view as ImageView).drawable as BitmapDrawable).bitmap).generate {
            if (!(activity?.isInValidState() == true)) {
                return@generate
            }
            it.vibrantSwatch?.let {
                val backgroundColor = it.rgb
                activity!!.supportActionBar!!.setBackgroundDrawable(ColorDrawable(backgroundColor))
                this.colors = ExtractedColors(backgroundColor, it.titleTextColor)
            }
            transitionsFramework.startPostponedEnterTransition(activity!!)
        }

    }

    private fun onNotLoaded() {
        if (activity?.isInValidState() == true) {
            transitionsFramework.startPostponedEnterTransition(activity!!)
        }
    }

    fun setActivity(activity: AppCompatActivity?) {
        this.activity = activity
    }

}

