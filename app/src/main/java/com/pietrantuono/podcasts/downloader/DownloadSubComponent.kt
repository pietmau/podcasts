package com.pietrantuono.podcasts.downloader

import com.pietrantuono.podcasts.downloader.service.DownloaderService
import com.pietrantuono.podcasts.repository.SaveAndDowloandEpisodeIntentService
import dagger.Subcomponent

//@Singleton
@Subcomponent()
interface DownloadSubComponent {
    fun inject(downloaderService: DownloaderService)
    fun inject(downloaderService: SaveAndDowloandEpisodeIntentService)
}