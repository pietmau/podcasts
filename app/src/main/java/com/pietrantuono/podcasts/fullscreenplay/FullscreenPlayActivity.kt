package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.DetailActivtyBase
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.FullScreenPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject


class FullscreenPlayActivity : DetailActivtyBase(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.simple_exo_player_view) lateinit var exoPlayerViewWithBackground: FullScreenPlaybackControlView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App).applicationComponent?.with(FullscreenModule(this@FullscreenPlayActivity))?.inject(this@FullscreenPlayActivity)
        ButterKnife.bind(this@FullscreenPlayActivity)
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
        if (intent?.getBooleanExtra(STARTED_WITH_TRANSITION, false) == true) {
            enterWithTransition()
        } else {
            enterWithoutTransition()
        }
    }

    override fun getImageUrl(): String? {
        return intent?.getStringExtra(ARTWORK)
    }


}