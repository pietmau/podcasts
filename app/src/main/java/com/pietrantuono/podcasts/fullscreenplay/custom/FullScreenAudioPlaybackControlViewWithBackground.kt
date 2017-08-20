package com.pietrantuono.podcasts.fullscreenplay.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader


class FullScreenAudioPlaybackControlViewWithBackground : FrameLayout {
    @BindView(R.id.controller) lateinit var playbackControlView: PlaybackControlView
    @BindView(R.id.image) lateinit var imageView: ImageView
    private lateinit var imageLoader: SimpleImageLoader

    var imageUrl: String?
        get() = null
        set(value) {
            imageLoader.displayImage(value, imageView)
        }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.simple_exo_player_view_wrapper, this, true)
        performInjection()
        playbackControlView.player = ((context.applicationContext as App).applicationComponent!!).simpleExoPlayer()
        playbackControlView.showTimeoutMs = -1
        playbackControlView.show()
        imageView.setImageResource(R.drawable.podcast_grey_icon_very_big)
    }

    private fun performInjection() {
        imageLoader = (context.applicationContext as App).applicationComponent!!.simpleImageLoader()
        ButterKnife.bind(this@FullScreenAudioPlaybackControlViewWithBackground)
    }

}