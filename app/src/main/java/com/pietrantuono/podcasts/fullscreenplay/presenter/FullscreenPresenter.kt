package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView


class FullscreenPresenter(private val apiLevelChecker: ApiLevelChecker) {
    private var view: FullscreenPlayView? = null
        set(value) {
            view = value
        }

    fun onStart(fullscreenPlayActivity: FullscreenPlayActivity, intExtra: Int, booleanExtra: Boolean) {}

    fun onStop() {}


}