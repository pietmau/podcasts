package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.graphics.ColorUtils
import android.support.v4.graphics.drawable.DrawableCompat
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.apis.Episode

class DrawableHelper(private val resources: Resources) {

    companion object {
        private val RADIUS = 32f
        private val TRANSPARENCY: Float = 80f
    }

    var episode: Episode? = null

    private fun setAndTintDowloadDrawable(color: Int): Drawable? {
        return getAndTintDrawable(getDowloadedDrawbale(), color)
    }

    private fun getDowloadedDrawbale(): Int? {
        if (episode == null) {
            return null
        }
        if (episode!!.downloaded) {
            return R.drawable.ic_cloud_done_white_24dp
        } else {
            return R.drawable.ic_cloud_download_white_24dp
        }
    }

    private fun getAndTintDrawable(drawableResource: Int?, color: Int): Drawable? {
        if (drawableResource == null) {
            return null
        }
        val drawable = DrawableCompat.wrap(VectorDrawableCompat.create(resources, drawableResource, null)!!)
        DrawableCompat.setTint(drawable, color)
        return drawable
    }

    private fun setAndTintDurationDrawable(color: Int): Drawable? {
        val drawableResource = R.drawable.ic_access_time_black_24dp
        val drawable = getAndTintDrawable(drawableResource, color)
        return drawable
    }

    private fun getBackgroundDrawable(backgroundColor: Int): Drawable? {
        return GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS)
            setColor(backgroundColor)
        }
    }

    fun tintDrawables(color: ColorForBackgroundAndText) {
        color.bodyTextColor?.let {
            setAndTintDurationDrawable(it)
            setAndTintDowloadDrawable(it)
        }
    }

    fun setBackgroundColor(container: RelativeLayout, it: ColorForBackgroundAndText) {
        it.backgroundColor?.let {
            container.setBackgroundDrawable(getBackgroundDrawable(ColorUtils.setAlphaComponent(it,
                    ((TRANSPARENCY / 100) * 255).toInt())))
        }
    }


}