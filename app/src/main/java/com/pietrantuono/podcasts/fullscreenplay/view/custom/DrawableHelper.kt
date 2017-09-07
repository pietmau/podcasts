package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.graphics.ColorUtils
import android.support.v4.graphics.drawable.DrawableCompat
import android.widget.ImageView
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.apis.Episode

class DrawableHelper(private val resources: Resources) {
    private var colorForBackgroundAndText: ColorForBackgroundAndText? = null
    var episode: Episode? = null

    companion object {
        private val RADIUS = 32f
        private val TRANSPARENCY: Float = 80f
    }

    private fun getDownloadedDrawbale(): Int? {
        if (episode == null) {
            return null
        }
        if (episode!!.downloaded) {
            return R.drawable.ic_cloud_done_white_24dp
        } else {
            return R.drawable.ic_cloud_download_white_24dp
        }
    }

    private fun getAndTintDrawable(drawableResource: Int?, color: Int, view: ImageView) {
        if (drawableResource == null) {
            return
        }
        val drawable = DrawableCompat.wrap(VectorDrawableCompat.create(resources, drawableResource, null)!!)
        DrawableCompat.setTint(drawable, color)
        view.setImageDrawable(drawable)
    }

    private fun getBackgroundDrawable(backgroundColor: Int): Drawable? {
        return GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS)
            setColor(backgroundColor)
        }
    }

    fun tintDrawables(downloadedDrawbale: ImageView, durationView: ImageView) {
        colorForBackgroundAndText?.bodyTextColor?.let {
            getAndTintDrawable(R.drawable.ic_access_time_black_24dp, it, durationView)
            getAndTintDrawable(getDownloadedDrawbale(), it, downloadedDrawbale)
        }
    }

    fun setBackgroundColor(container: RelativeLayout, colorForBackgroundAndText: ColorForBackgroundAndText,
                           downloadedDrawbale: ImageView, durationView: ImageView) {
        this.colorForBackgroundAndText = colorForBackgroundAndText
        colorForBackgroundAndText.backgroundColor?.let {
            container.setBackgroundDrawable(getBackgroundDrawable(ColorUtils.setAlphaComponent(it,
                    ((TRANSPARENCY / 100) * 255).toInt())))
        }
        tintDrawables(downloadedDrawbale, durationView)
    }


}