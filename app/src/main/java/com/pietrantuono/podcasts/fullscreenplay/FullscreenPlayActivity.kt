package com.pietrantuono.podcasts.fullscreenplay

import android.animation.Animator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
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
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import com.pietrantuono.podcasts.utils.isInValidState
import com.pietrantuono.podcasts.utils.isLollipopOrHigher
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
    private val TRANSITION_DURATION: Long = 200
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.simple_exo_player_view) lateinit var controlView: ColorizedPlaybackControlView
    private var controlViewTop: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App).applicationComponent?.with(FullscreenModule())?.inject(this@FullscreenPlayActivity)
        ButterKnife.bind(this@FullscreenPlayActivity)
        if (savedInstanceState == null && isLollipopOrHigher) {
            addOnGlobalLayoutListener()
            enterWithTransition()
        } else {
            enterWithoutTransition()
        }
        setImageAndColors()
        setUpActionBar()
    }

    private fun addOnGlobalLayoutListener() {
        controlView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                controlView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                controlViewTop = controlView.top
                controlView.y = window.decorView.bottom.toFloat()
            }
        })
    }

    @SuppressLint("NewApi")
    override fun enterWithTransition() {
        transitions.initDetailTransitions(this, imageView, object : SimpleTransitionListener() {
            override fun onTransitionEnd(transition: Transition?) {
                animateControlsIn()
                transition?.let { it.removeListener(this) }
            }
        })
    }

    private fun animateControlsIn() {
        if (isInValidState() && controlViewTop != null) {
            controlView.animate().y(controlViewTop!!.toFloat()).setDuration(TRANSITION_DURATION).start()
        }
    }

    override fun enterWithoutTransition() {
        controlViewTop?.let {
            controlView.y = it.toFloat()
        }
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    private fun setImageAndColors() {
        val backgroundColor = getBackgroundColor()
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

    override fun onBackPressed() {
        if (!isLollipopOrHigher) {
            finish()
        } else {
            animateControlsOut()
        }
    }

    private fun animateControlsOut() {
        if (isInValidState()) {
            controlView
                    .animate()
                    .y(window.decorView.bottom.toFloat())
                    .setDuration(TRANSITION_DURATION)
                    .setListener(object : SimpleAnimatorListener() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        override fun onAnimationEnd(animator: Animator?) {
                            animator?.removeAllListeners()
                            finishAfterTransition()
                        }
                    })
        }
    }
}