package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.player.player.service.PlayerService


class FullscreenPresenter(private val model: FullscreenModel) {
    private var view: FullscreenPlayView? = null
    private var episode: Episode? = null
    private var notificatorService: NotificatorService? = null
    private var player: Player? = null

    private var connection: ServiceConnection? = object : ServiceConnection {
        override fun onServiceDisconnected(componentName: ComponentName?) {}

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            notificatorService = iBinder as? NotificatorService
            player = iBinder as? Player
            episode?.let {
                player?.setEpisode(it)
            }
            notificatorService?.boundToFullScreen = true
            notificatorService?.checkIfShouldNotify()
        }
    }

    fun onCreate(view: FullscreenPlayView, url: String?, fromSavedState: Boolean) {
        this.view = view
        model.getEpisodeByUrl(url)
    }

    private fun setTitle(title: String?) {
        view?.title = title
    }

    fun onStart(activity: Activity) {
        model.subscribe(object : SimpleObserver<Episode>() {
            override fun onNext(episode: Episode) {
                this@FullscreenPresenter.episode = episode
                view?.setEpisode(episode)
            }

            override fun onError(throwable: Throwable?) {
                view?.setEpisode(null)
            }
        })
        bindService(activity)
    }

    fun onStop(activity: Activity) {
        view = null
        model.unSubscribe()
        unbindService(activity)
    }

    private fun setEpisode(episode: Episode) {
        player?.setEpisode(episode)
    }

    public fun sendEpisodeToPlayer() {

    }

    private fun bindService(activity: Activity) {
        activity.bindService(Intent(activity, PlayerService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService(activity: Activity) {
        notificatorService?.boundToFullScreen = false
        notificatorService?.checkIfShouldNotify()
        notificatorService = null
        player = null
        activity.unbindService(connection)
    }

}