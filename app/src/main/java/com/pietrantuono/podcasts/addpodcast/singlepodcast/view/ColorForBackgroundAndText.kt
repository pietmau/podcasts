package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.support.v4.graphics.ColorUtils

class ColorForBackgroundAndText(var backgroundColor: Int? = null, val titleTextColor: Int? = null,
                                val bodyTextColor: Int? = null) {
    init {
        backgroundColor?.let {
            backgroundColor = ColorUtils.setAlphaComponent(it, (255 * 95 / 100))
        }
    }
}