package com.pietrantuono.podcasts.player

import models.pojos.Episode


interface SimplePlayer {
    fun playEpisode(episode: Episode?)
    fun onStop()
}