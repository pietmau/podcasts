package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.SimpleExoPlayerViewWrapper
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.utils.SINGLE_PODCAST_TRACK_ID
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject


class FullscreenPlayActivity : AppCompatActivity(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.simple_exo_player_view) lateinit var exoPlayerView: SimpleExoPlayerViewWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App).applicationComponent?.with(FullscreenModule())?.inject(this@FullscreenPlayActivity)
        ButterKnife.bind(this@FullscreenPlayActivity)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent
                .getIntExtra(SINGLE_PODCAST_TRACK_ID, -1), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}