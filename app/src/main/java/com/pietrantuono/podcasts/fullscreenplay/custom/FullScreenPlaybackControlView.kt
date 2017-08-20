package com.pietrantuono.podcasts.fullscreenplay.custom

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.graphics.ColorUtils
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ExtractedColors
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.COLORS

class FullScreenPlaybackControlView : FrameLayout {
    @BindView(R.id.controller) lateinit var playbackControlView: PlaybackControlView
    @BindView(R.id.image) lateinit var imageView: ImageView
    private lateinit var imageLoader: SimpleImageLoader

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        (context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.simple_exo_player_view_wrapper, this, true)
        performInjection()
        playbackControlView.showTimeoutMs = -1
        playbackControlView.show()
    }

    private fun performInjection() {
        ButterKnife.bind(this@FullScreenPlaybackControlView)
        val applicationComponent = (context.applicationContext as App).applicationComponent!!
        playbackControlView.player = applicationComponent.simpleExoPlayer()
        imageLoader = applicationComponent.simpleImageLoader()
    }

    fun setImageAndColors(intent: Intent) {
        intent?.let {
            setImage(it)
            setColors(it)
        }
    }

    private fun setColors(intent: Intent) {
        intent.getParcelableExtra<ExtractedColors>(COLORS)?.let {
            val backgroundColor = ColorUtils.setAlphaComponent(it.backgroundColor, 255 * 8 / 10)
            playbackControlView.setBackgroundColor(backgroundColor)
        }
    }

    private fun setImage(intent: Intent) {
        intent.getStringExtra(ARTWORK)?.let { imageLoader.displayImage(it, imageView) }
    }

}