package com.pietrantuono.podcasts.singlepodcast.view.custom

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.singlepodcast.customviews.SimpleContolView


class CoordinatorWithBottomMargin(context: Context?, attrs: AttributeSet?) : CoordinatorLayout(context, attrs) {
    private var playbackControls: SimpleContolView? = null
    private var playbackControlsHeight = 0

    fun setUpPlayerControls(playbackControls: SimpleContolView) {
        this.playbackControls = playbackControls
        playbackControls.waitForLayout({ height ->
            playbackControlsHeight = height
            setBottomMargin()
        })
        playbackControls.setVisibilityListener {
            setBottomMargin()
        }
        setBottomMargin()
    }

    private fun setBottomMargin() {
        if (playbackControls?.visibility == View.VISIBLE) {
            val params = layoutParams as RelativeLayout.LayoutParams
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, playbackControlsHeight)
        } else {
            val params = layoutParams as RelativeLayout.LayoutParams
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, 0)
        }
    }
}