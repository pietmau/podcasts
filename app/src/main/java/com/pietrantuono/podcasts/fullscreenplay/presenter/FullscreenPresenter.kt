package com.pietrantuono.podcasts.fullscreenplay.presenter

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
        player?.playEpisode(episode)
        episode.title?.let { view.title = it }
        val url = episode.imageUrl
        if (url != null) {
            view.setImage(url)
        } else {
            view.startTransitionPostponed()
        }
    }

}