package com.pietrantuono.podcasts.fullscreenplay.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App


class SimpleExoPlayerViewWrapper : FrameLayout {
    private val simpleExoPlayerView: SimpleExoPlayerView

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.simple_exo_player_view_wrapper, this, true)
        simpleExoPlayerView = findViewById(R.id.simple) as SimpleExoPlayerView
        simpleExoPlayerView.player = ((context.applicationContext as App).applicationComponent!!).simpleExoPlayer()
        simpleExoPlayerView.controllerShowTimeoutMs = -1
        simpleExoPlayerView.showController()
    }
}