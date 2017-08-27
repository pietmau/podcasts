package com.pietrantuono.podcasts.fullscreenplay.presenter

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayView
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository


class FullscreenPresenter(private val episodesRepository: EpisodesRepository, private val player: Player?,
                          private val creator: MediaSourceCreator) {
    private var view: FullscreenPlayView? = null
    private var episode: Episode? = null

    fun onStart(view: FullscreenPlayView, url: String?) {
        this.view = view
        if (episode == null) {
            episode = episodesRepository.getEpisodeByUrl(url)
            episode?.let { setMediaSource(it) }
        }
        episode?.let {
            setTitle(it.title)
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

    private fun setMediaSource(episode: Episode) {
        creator.getMediaSourceFromSingleEpisode(episode)?.let {
            player?.setMediaSource(it)
        }

    }


}