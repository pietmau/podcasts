package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.fullscreenplay.view.custom.TransportControlsWrapper


class FullscreenPresenter(
        private val model: FullscreenModel)
    : ViewModel(), ColorizedPlaybackControlView.Callback {

    private var view: FullscreenPlayView? = null
    private var mediaBrowserWrapper: TransportControlsWrapper? = null

    fun bindView(fullscreenPlayActivity: FullscreenPlayView) {
        this.view = fullscreenPlayActivity
    }

    fun onCreate(context: Context, view: FullscreenPlayView, url: String?, fromSavedState: Boolean) {
        if (!fromSavedState) {
            model.getEpisodeByUrlAsync(url)
        }
        mediaBrowserWrapper = TransportControlsWrapper(context)
    }

    fun onStart() {
        mediaBrowserWrapper?.onStart()
        model.subscribe(object : SimpleObserver<Episode>() {
            override fun onNext(episode: Episode) {
                onEpisodeAvailable(episode)
            }
        })
    }

    private fun onEpisodeAvailable(episode: Episode?) {
        view?.setEpisode(episode)//TODO enable only when available
    }

    fun onStop() {
        mediaBrowserWrapper?.onStop()
        view = null
        model.unSubscribe()
    }

    override fun onPlayClicked() {
        mediaBrowserWrapper?.playFromMediaId(model.episode)
    }

    override fun onPlayerError(errorMessage: CharSequence?) {
        view?.onError(errorMessage)
    }
}