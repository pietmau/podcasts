package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import com.pietrantuono.podcasts.downloader.downloader.Downloader
import models.pojos.Episode

class DownloadOrStreamManagerImpl(private val downloader: Downloader) : DownloadOrStreamManager {

    override fun onPlayClicked(episode: Episode?, view: CustomControls?) {
        episode?.uri?.let {
            downloader.downloadAndPlayFromUri(it)
        }

    }
}