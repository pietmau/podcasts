package com.pietrantuono.podcasts.fullscreenplay.presenter

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.player.player.service.PlayerService
import com.pietrantuono.podcasts.repository.EpisodesRepository


class FullscreenPresenter(private val episodesRepository: EpisodesRepository,
                          private val creator: MediaSourceCreator) {
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

    fun onStart(view: FullscreenPlayView, url: String?) {
        this.view = view
        if (episode == null) {
            episode = episodesRepository.getEpisodeByUrl(url)
        }
        episode?.let {
            view?.setEpisode(it)
            setImage(it.imageUrl)
        }
    }

    private fun setImage(url: String?) {
        if (url != null) {
            view?.loadImage(url)
        } else {
            view?.startTransitionPostponed()
        }
    }

    private fun setTitle(title: String?) {
        view?.title = title
    }

    fun onStop() {
        view = null
    }

    private fun setEpisode(episode: Episode) {
        player?.setEpisode(episode)
    }

    public fun sendEpisodeToPlayer() {

    }

    fun bindService(activity: Activity) {
        activity.bindService(Intent(activity, PlayerService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(activity: Activity) {
        notificatorService?.boundToFullScreen = false
        notificatorService?.checkIfShouldNotify()
        notificatorService = null
        player = null
        activity.unbindService(connection)
    }

}