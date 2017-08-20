package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.FullScreenAudioPlaybackControlViewWithBackground
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_LINK
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject


class FullscreenPlayActivity : AppCompatActivity(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @Inject lateinit var imageLoader: SimpleImageLoader
    @BindView(R.id.simple_exo_player_view) lateinit var exoPlayerViewWithBackground: FullScreenAudioPlaybackControlViewWithBackground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_player_activity)
        (application as App).applicationComponent?.with(FullscreenModule())?.inject(this@FullscreenPlayActivity)
        ButterKnife.bind(this@FullscreenPlayActivity)
        setImage()
    }

    private fun setImage() {
        intent?.let {
            it.getStringExtra(ARTWORK)?.let {
                exoPlayerViewWithBackground.imageUrl = it
            }
        }
    }

    private fun activityIsInAValidState(): Boolean {
        return !(isFinishing || isDestroyed || isChangingConfigurations)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent
                .getStringExtra(EPISODE_LINK), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }


}