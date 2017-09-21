package com.pietrantuono.podcasts.player.player.service.model

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository

class PlayerServiceModelImpl(private val repo:EpisodesRepository) : PlayerServiceModel {
    override var currentlyPlayingEpisode: Episode? = null

    override fun getEpisodeByUrl(mediaId: String?) {
        currentlyPlayingEpisode = repo.getEpisodeByUrl(mediaId)
    }
}