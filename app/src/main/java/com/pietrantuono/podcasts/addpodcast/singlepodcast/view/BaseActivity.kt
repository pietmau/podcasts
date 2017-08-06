package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.SimpleControlView


open class BaseActivity : AppCompatActivity() {
    @BindView(R.id.playbackcontrols) lateinit var playbackControls: SimpleControlView

    override fun onStop() {
        super.onStop()
        playbackControls.removeCallback()
    }

    override fun onStart() {
        super.onStart()
        playbackControls.setCallback()
    }
}