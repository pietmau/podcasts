package com.pietrantuono.podcasts.singlepodcast.view.custom

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.widget.RelativeLayout


class CoordinatorWithBottomMargin(context: Context?, attrs: AttributeSet?) : CoordinatorLayout(context, attrs) {

    fun setBottomMargin(height: Int) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, height)
    }
}