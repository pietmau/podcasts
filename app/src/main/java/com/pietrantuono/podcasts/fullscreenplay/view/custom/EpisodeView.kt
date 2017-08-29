package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.R


class EpisodeView : RelativeLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.episode_view, this, true)
    }
}