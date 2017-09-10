package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.player.player.player.Player


class FullscreenPresenter(private val model: FullscreenModel, private val player: Player?,
                          private val connector: ServiceConnector, private val apiLevelChecker: ApiLevelChecker)
    : ViewModel(), ColorizedPlaybackControlView.Callback {

    private var view: FullscreenPlayView? = null

    fun onCreate(view: FullscreenPlayView, url: String?, fromSavedState: Boolean) {
        this.view = view
        if (!fromSavedState) {
            model.getEpisodeByUrl(url)
        }
    }

    fun onStart(activity: Activity) {
        connector.bindService(activity)
        model.subscribe(object : SimpleObserver<Episode>() {
            override fun onNext(episode: Episode) {
                onEpisodeAvailable(episode)
            }
        })
    }

    private fun onEpisodeAvailable(episode: Episode?) {
        view?.setEpisode(episode)//TODO enable only when available
    }

    fun onStop(activity: Activity) {
        view = null
        model.unSubscribe()
        connector.unbindService(activity)
    }

    override fun onPlayClicked() {
        model.episode?.let { player?.setEpisode(it) }
    }

}