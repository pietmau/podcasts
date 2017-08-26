package com.pietrantuono.podcasts.fullscreenplay.presenter

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository


class FullscreenPresenter(private val episodesRepository: EpisodesRepository, private val player: Player?) {
    private var view: FullscreenPlayView? = null

    fun onStart(view: FullscreenPlayView, url: String?) {
        this.view = view
        val episode = episodesRepository.getEpisodeByUrl(url)
        if (episode == null) {
            return
        }
        startPlaying(episode)
        setTitle(episode?.title)
        val url = episode.imageUrl
        setImage(url, view)
    }

    private fun setImage(url: String?, view: FullscreenPlayView) {
        if (url != null) {
            view.loadImage(url)
        } else {
            view.startTransitionPostponed()
        }
    }

    private fun setTitle(title: String?) {
        title?.let { view?.title = it }
    }

    private fun startPlaying(episode: Episode) {
        player?.playEpisode(episode)
    }

}