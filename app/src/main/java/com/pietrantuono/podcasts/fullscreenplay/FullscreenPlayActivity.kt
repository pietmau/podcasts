package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AbstractBaseDetailActivty
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.CustomControlsImpl
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.fullscreenplay.view.custom.EpisodeView
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_URI
import models.pojos.Episode
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.control) lateinit var controlView: CustomControlsImpl
    @BindView(R.id.episodeView) lateinit var episodeView: EpisodeView
    @BindView(R.id.root) lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App)
                .applicationComponent
                ?.with(FullscreenModule(this))
                ?.inject(this@FullscreenPlayActivity)
        initViews()
        loadImage(intent?.getStringExtra(ARTWORK))
        presenter.bindView(this)
        presenter.onCreate(this, this, intent?.getStringExtra(EPISODE_URI), savedInstanceState != null)
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }

    private fun initViews() {
        ButterKnife.bind(this@FullscreenPlayActivity)
        controlView.callback = presenter
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
        controlView.setEpisode(episode)
    }

    override fun onColorExtractionCompleted() {
        episodeView.setColors(colorExtractor.colorForBackgroundAndText)
        controlView.setColors(colorExtractor.colorForBackgroundAndText)
    }

    override fun getImageUrl(): String? = null

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }

    override fun onError(errorMessage: CharSequence?) {
        val message = errorMessage ?: resources.getString(R.string.player_error)
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
    }

}

