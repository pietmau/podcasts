package com.pietrantuono.podcasts.fullscreenplay

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.Transitions
import com.pietrantuono.podcasts.utils.BACKGROUND_COLOR
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import com.pietrantuono.podcasts.utils.isInValidState
import javax.inject.Inject

class FullscreenPlayActivity : AppCompatActivity(), FullscreenPlayView {
    @Inject lateinit var transitions: Transitions
    @Inject lateinit var presenter: FullscreenPresenter
    @Inject lateinit var imageLoader: SimpleImageLoader
    @BindView(R.id.simple_exo_player_view) lateinit var controlView: ColorizedPlaybackControlView
    @BindView(R.id.toolbar) lateinit var tooolbar: Toolbar
    @BindView(R.id.image) lateinit var imageView: ImageView

    override var title: String?
        get() = tooolbar?.title?.toString()
        set(string) {
            tooolbar?.let { it.setTitle(string) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App).applicationComponent?.with(FullscreenModule())?.inject(this@FullscreenPlayActivity)
        ButterKnife.bind(this@FullscreenPlayActivity)
        if (intent?.getBooleanExtra(STARTED_WITH_TRANSITION, false) == true) {
            enterWithTransition()
        } else {
            enterWithoutTransition()
        }
        setImageAndColors()
    }

    private fun setImageAndColors() {
        val backgroundColor = intent?.getIntExtra(BACKGROUND_COLOR, resources.getColor(R.color.colorPrimary)) ?: resources.getColor(R.color.colorPrimary)
        controlView.setBackgroundColors(backgroundColor)
        tooolbar.setBackgroundColor(backgroundColor)
    }

    override fun setImage(imageUrl: String) {
        imageLoader.displayImage(imageUrl, imageView, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                startTransitionPostponed()
            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {
                startTransitionPostponed()
            }

            override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                startTransitionPostponed()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent?.getStringExtra(EPISODE_LINK))
    }

    fun enterWithTransition() {
        transitions.initDetailTransitions(this)
    }

    fun enterWithoutTransition() {
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    override fun startTransitionPostponed() {
        if (isInValidState()) {
            transitions.startPostponedEnterTransition(this)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }
}