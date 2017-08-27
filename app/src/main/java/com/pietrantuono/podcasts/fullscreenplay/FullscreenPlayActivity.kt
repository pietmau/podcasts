package com.pietrantuono.podcasts.fullscreenplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Transition
import android.view.ViewTreeObserver
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AbstractBaseDetailActivty
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.utils.BACKGROUND_COLOR
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import com.pietrantuono.podcasts.utils.isLollipopOrHigher
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.simple_exo_player_view) lateinit var controlView: ColorizedPlaybackControlView
    private var controlViewTop: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App).applicationComponent?.with(FullscreenModule())?.inject(this@FullscreenPlayActivity)
        ButterKnife.bind(this@FullscreenPlayActivity)
        controlView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                controlView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                controlViewTop = controlView.top
                controlView.y = window.decorView.bottom.toFloat()
            }
        })

        if (startedWithTransition() && isLollipopOrHigher) {
            enterWithTransition()
        } else {
            enterWithoutTransition()
        }
        setImageAndColors()
        setUpActionBar()
    }

    @SuppressLint("NewApi")
    override fun enterWithTransition() {
        transitions.initDetailTransitions(this, imageView, object : SimpleTransitionListener() {
            override fun onTransitionCancel(transition: Transition?) {
                animateControlIn()
            }

            override fun onTransitionEnd(transition: Transition?) {
                animateControlIn()
            }
        })
    }

    private fun animateControlIn() {
        controlViewTop?.let {
            controlView.animate().y(it.toFloat()).setDuration(1000).start()
        }
    }

    override fun enterWithoutTransition() {
        controlViewTop?.let {
            controlView.y = it.toFloat()
        }
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    private fun setImageAndColors() {
        val backgroundColor = intent?.getIntExtra(BACKGROUND_COLOR, resources.getColor(R.color.colorPrimary)) ?: resources.getColor(R.color.colorPrimary)
        controlView.setBackgroundColors(backgroundColor)
        setToolbarColor(backgroundColor)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent?.getStringExtra(EPISODE_LINK))
    }

    override fun getImageUrl(): String? {
        return null
    }
}