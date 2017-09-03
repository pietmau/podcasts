package com.pietrantuono.podcasts.fullscreenplay

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.view.custom.EpisodeView
import com.pietrantuono.podcasts.utils.TRANSITION_DURATION
import com.pietrantuono.podcasts.utils.isInValidState

class AnimationsHelper {
    var controlViewTop: Int? = null

    fun animateViewsIn(activity: AppCompatActivity, controlView: ColorizedPlaybackControlView, episodeView: EpisodeView) {
        if (!activity.isInValidState()) {
            return
        }
        if (controlViewTop != null) {
            controlView
                    .animate()
                    .y(controlViewTop!!
                            .toFloat())
                    .setDuration(TRANSITION_DURATION)
                    .start()
        }
        episodeView
                .animate()
                .alpha(1.0f)
                .setDuration(TRANSITION_DURATION)
                .start()
    }

    fun setViewsImmediately(controlView: ColorizedPlaybackControlView, episodeView: EpisodeView) {
        controlViewTop?.let { controlView.y = it.toFloat() }
        episodeView.alpha = 1.0f
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateControlsOut(activity: AppCompatActivity, controlView: ColorizedPlaybackControlView, episodeView: EpisodeView) {
        if (!activity.isInValidState()) {
            return
        }
        val bottom = activity.window.decorView.bottom.toFloat()
        controlView
                .animate()
                .y(bottom)
                .setDuration(TRANSITION_DURATION)
                .withEndAction { activity.finishAfterTransition() }
        episodeView
                .animate()
                .alpha(0.0f)
                .setDuration(TRANSITION_DURATION)
                .start()
    }

}