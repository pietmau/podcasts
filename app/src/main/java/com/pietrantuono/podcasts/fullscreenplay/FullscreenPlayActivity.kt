package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.FullScreenPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject

class FullscreenPlayActivity : AppCompatActivity(), FullscreenPlayView, FullScreenPlaybackControlView.Callback {
    @Inject lateinit var transitionsFramework: TransitionsFramework
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.simple_exo_player_view) lateinit var exoPlayerViewWithBackground: FullScreenPlaybackControlView

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
        intent?.let {
            exoPlayerViewWithBackground.setImageAndColors(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent?.getStringExtra(EPISODE_LINK))

    }

    fun enterWithTransition() {
        transitionsFramework.initDetailTransitions(this)
    }

    fun enterWithoutTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun exitWithoutSharedTransition() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun exitWithSharedTrsnsition() {
        super.onBackPressed()
    }

    override fun onImageLoadedSuccessfullyOrUnsuccessfully() {
        transitionsFramework.startPostponedEnterTransition(this)
    }

}