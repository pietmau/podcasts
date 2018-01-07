package com.pietrantuono.podcasts.fullscreenplay

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AbstractBaseDetailActivty
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.CustomControls
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.CustomControlsImpl
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.fullscreenplay.view.custom.EpisodeView
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_URI
import models.pojos.Episode
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
    companion object {
        const val SHOULD_STREAM_AUDIO = "should_stream"
    }

    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.control) lateinit var controlView: CustomControlsImpl
    @BindView(R.id.episodeView) lateinit var episodeView: EpisodeView
    @BindView(R.id.root) lateinit var root: View
    private val URI: String? = "uri"
    private var uri: String? = null
    private var shouldStream = false

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App)
                .appComponent
                ?.with(FullscreenModule(this))
                ?.inject(this@FullscreenPlayActivity)
        initViews()
        uri = getUri(intent, savedState)
        shouldStream = getShouldStream(intent, savedState)
        presenter.onCreate(this, uri)
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }

    private fun getShouldStream(intent: Intent?, savedState: Bundle?) = savedState?.getBoolean(SHOULD_STREAM_AUDIO) ?: (intent?.getBooleanExtra(SHOULD_STREAM_AUDIO, false) ?: false)

    private fun getUri(intent: Intent, savedState: Bundle?): String? = savedState?.getString(URI) ?: intent?.getStringExtra(EPISODE_URI)

    private fun initViews() {
        ButterKnife.bind(this@FullscreenPlayActivity)
        controlView.callback = presenter
        loadImage(intent?.getStringExtra(ARTWORK))
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
        controlView.setConfiguration(getConfiguration())
        controlView.onStart()
    }

    private fun getConfiguration(): CustomControls.Configuration = CustomControls.Configuration(shouldStream)

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        controlView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(URI, uri)
        outState?.putBoolean(SHOULD_STREAM_AUDIO, shouldStream)
        super.onSaveInstanceState(outState)
    }

    override fun setEpisode(episode: Episode?) {
        episodeView.setEpisode(episode)
        controlView.setEpisode(episode)
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

