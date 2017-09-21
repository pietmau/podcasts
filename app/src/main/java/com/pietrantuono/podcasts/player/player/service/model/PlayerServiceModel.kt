package com.pietrantuono.podcasts.player.player.service.model

import com.pietrantuono.podcasts.apis.Episode


interface PlayerServiceModel {
    fun getEpisodeByUrl(mediaId: String?)
    val currentlyPlayingEpisode: Episode?
}