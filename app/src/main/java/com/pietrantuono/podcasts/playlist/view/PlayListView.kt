package com.pietrantuono.podcasts.playlist.view

import models.pojos.Episode

interface PlayListView {
    fun onPlaylistRetrieved(playlist: List<Episode>)

}