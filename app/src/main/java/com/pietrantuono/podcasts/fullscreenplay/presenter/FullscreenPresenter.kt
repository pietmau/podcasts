package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.player.player.player.Player
import com.pietrantuono.podcasts.player.player.service.PlayerService


class FullscreenPresenter(
        private val model: FullscreenModel,
        private val player: Player?,
        private val connector: ServiceConnector)
    : ViewModel(), ColorizedPlaybackControlView.Callback {

    private var view: FullscreenPlayView? = null

    private var mediaBrowser: MediaBrowserCompat? = null

    fun onCreate(context: Context, view: FullscreenPlayView, url: String?, fromSavedState: Boolean) {
        this.view = view
        if (!fromSavedState) {
            model.getEpisodeByUrl(url)
        }
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, PlayerService::class.java),
                object : MediaBrowserCompat.ConnectionCallback() {
                    override fun onConnected() {

                    }

                    override fun onConnectionSuspended() {

                    }

                    override fun onConnectionFailed() {

                    }
                }, null)
    }

    fun onStart(activity: Activity) {
        //connector.bindService(activity)
        mediaBrowser?.connect()
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
        mediaBrowser?.disconnect()
        //connector.unbindService(activity)
    }

    override fun onPlayClicked() {
        model.episode?.let { player?.setEpisode(it) }
    }

}