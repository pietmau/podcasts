package com.pietrantuono.podcasts.singlepodcast.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar


class SimpleProgressBar(context: Context?, attrs: AttributeSet?) : ProgressBar(context, attrs) {

    var showProgress: Boolean = false
    set(value) {
        if (value) {
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }
}