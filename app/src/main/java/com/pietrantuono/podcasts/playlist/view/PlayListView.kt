package com.pietrantuono.podcasts.playlist.view

import models.pojos.Episode

interface PlayListView {
    fun onPlaylistRetrieved(playlist: List<Episode>)
    fun onEpisodeRetrieved(episode: Episode)
    var currentlyPlayingMediaId: String?

}