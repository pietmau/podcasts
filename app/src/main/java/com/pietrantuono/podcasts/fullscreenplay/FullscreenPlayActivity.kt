package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AbstractBaseDetailActivty
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.customcontrols.CustomControls
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.fullscreenplay.view.custom.EpisodeView
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.control) lateinit var controlView: CustomControls
    @BindView(R.id.episodeView) lateinit var episodeView: EpisodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App)
                .applicationComponent
                ?.with(FullscreenModule(this))
                ?.inject(this@FullscreenPlayActivity)
        initViews()
        loadImage(intent?.getStringExtra(ARTWORK))
        presenter.onCreate(this, this, intent?.getStringExtra(EPISODE_LINK), savedInstanceState != null)
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }

    private fun initViews() {
        ButterKnife.bind(this@FullscreenPlayActivity)
        controlView.setBackgroundColors(getBackgroundColor())
        controlView.setCallback(presenter)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        controlView.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        controlView.onStop()
    }

    override fun setEpisode(episode: Episode?) {
        episodeView.setEpisode(episode)
    }

    override fun onColorExtractionCompleted() {
        episodeView.setColors(colorExtractor.colorForBackgroundAndText)
    }

    override fun getImageUrl(): String? = null

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }
}

