package com.pietrantuono.podcasts.fullscreenplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Transition
import android.view.ViewTreeObserver
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AbstractBaseDetailActivty
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.fullscreenplay.view.custom.EpisodeView
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
        @Inject lateinit var presenter: FullscreenPresenter
    @Inject lateinit var apiLevelChecker: ApiLevelChecker
    @Inject lateinit var animationsHelper: AnimationsHelper
    @BindView(R.id.control) lateinit var controlView: ColorizedPlaybackControlView
    @BindView(R.id.episodeView) lateinit var episodeView: EpisodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App)
                .applicationComponent
                ?.with(FullscreenModule(this))
                ?.inject(this@FullscreenPlayActivity)
        initViews()
        presenter.onCreate(this, intent?.getStringExtra(EPISODE_LINK), savedInstanceState != null)
    }

    private fun initViews() {
        ButterKnife.bind(this@FullscreenPlayActivity)
        setUpActionBar()
        controlView.setBackgroundColors(getBackgroundColor())
        setToolbarColor(getBackgroundColor())
    }

    override fun addOnGlobalLayoutListener() {
        controlView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                controlView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animationsHelper.controlViewTop = controlView.top
                controlView.y = window.decorView.bottom.toFloat()
            }
        })
    }

    @SuppressLint("NewApi")
    override fun enterWithTransition() {
        transitionsHelper.initDetailTransitions(this, imageView, object : SimpleTransitionListener() {
            override fun onTransitionEnd(transition: Transition?) {
                animationsHelper.animateViewsIn(this@FullscreenPlayActivity, controlView, episodeView)
                transition?.removeListener(this)
            }
        })
    }

    override fun enterWithoutTransition() {
        animationsHelper.setViewsImmediately(controlView, episodeView)
        super.enterWithoutTransition()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
        controlView.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop(this)
        controlView.onStop()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setEpisode(episode: Episode?) {
        episodeView.setEpisode(episode)
        title = episode?.title
        loadImage(episode?.imageUrl)
    }

    override fun onColorExtractionCompleted() {
        episodeView.setColors(colorExtractor.colorForBackgroundAndText)
        startTransitionPostponed()
    }

    override fun getImageUrl(): String? = null

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    override fun animateControlsOut() {
        animationsHelper.animateControlsOut(this, controlView, episodeView)
    }

}

