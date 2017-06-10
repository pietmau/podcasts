package com.pietrantuono.podcasts.playerview


import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.pietrantuono.podcasts.application.App
import javax.inject.Inject

class BottomPlayerView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    @Inject lateinit var bottomPlayerViewPresenter: BottomPlayerViewPresenter

    init {
        visibility = GONE
        (context.applicationContext as App).applicationComponent.inject(this@BottomPlayerView)
    }

    private fun ffff() {

    }

    fun start() {
        visibility = VISIBLE
    }
}

