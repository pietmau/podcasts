package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import models.pojos.Episode


interface DownloadOrStreamManager {

    fun onPlayClicked(episode: Episode?, view: CustomControls?)
}