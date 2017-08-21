package com.pietrantuono.podcasts.fullscreenplay.presenter

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository


class FullscreenPresenter(private val episodesRepository: EpisodesRepository, private val player: Player?) {
    private var view: FullscreenPlayView? = null
    private var episode: Episode? = null

    fun onStart(view: FullscreenPlayView, url: String?) {
        this.view = view
        episode = episodesRepository.getEpisodeByUrl(url)
        episode?.let { player?.playEpisode(it) }
    }

}