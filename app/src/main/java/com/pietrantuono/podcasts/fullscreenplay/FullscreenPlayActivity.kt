package com.pietrantuono.podcasts.fullscreenplay

import android.os.Bundle
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
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject

class FullscreenPlayActivity : AbstractBaseDetailActivty(), FullscreenPlayView {
    @Inject lateinit var presenter: FullscreenPresenter
    @BindView(R.id.simple_exo_player_view) lateinit var colorizedPlaybackControlView: ColorizedPlaybackControlView

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
        val backgroundColor = intent?.getIntExtra(BACKGROUND_COLOR, resources.getColor(R.color.colorPrimary)) ?: resources.getColor(R.color.colorPrimary)
        colorizedPlaybackControlView.setBackgroundColors(backgroundColor)
        setToolbarColor(backgroundColor)
    }

    override fun setImage(imageUrl: String) {
        loadImage(imageUrl)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent?.getStringExtra(EPISODE_LINK))
    }

    override fun getImageUrl(): String? {
        return null
    }
}