package com.pietrantuono.podcasts.fullscreenplay.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader


class FullScreenAudioPlaybackControlViewWithBackground : FrameLayout {
    @BindView(R.id.controller) lateinit var playbackControlView: PlaybackControlView
    @BindView(R.id.image) lateinit var imageView: ImageView
    private lateinit var imageLoader: SimpleImageLoader
    private var detached: Boolean = false

    var imageUrl: String?
        get() = null
        set(value) {
            imageLoader.displayImage(value, imageView, object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {

                }
            })
        }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.simple_exo_player_view_wrapper, this, true)
        performInjection()
        playbackControlView.player = ((context.applicationContext as App).applicationComponent!!).simpleExoPlayer()
        playbackControlView.showTimeoutMs = -1
        playbackControlView.show()
        tintDrawables()
    }

    private fun performInjection() {
        imageLoader = (context.applicationContext as App).applicationComponent!!.simpleImageLoader()
        ButterKnife.bind(this@FullScreenAudioPlaybackControlViewWithBackground)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        detached = true
    }

    private fun tintDrawables() {
        val drawable = (findViewById(R.id.exo_prev) as ImageView).drawable
        DrawableCompat.setTint(drawable, Color.RED)

    }
}