package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.SimpleControlView
import com.pietrantuono.podcasts.application.App


open class AbstractPlaybackControlsActivity : AppCompatActivity() { //TODO remove
    var playbackControls: SimpleControlView? = null

    fun initPlaybackControls() {
        playbackControls = findViewById<SimpleControlView>(R.id.playbackcontrols) as SimpleControlView?
    }

    override fun onStop() {
        super.onStop()
        playbackControls?.removeCallback()
        (applicationContext as App).unbindPlayerService()
    }

    override fun onStart() {
        super.onStart()
        playbackControls?.setCallback()
        (applicationContext as App).bindPlayerService()
    }

}