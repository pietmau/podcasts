package com.pietrantuono.podcasts.downloader

import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.di.DownloaderServiceScope
import com.pietrantuono.podcasts.downloader.service.DownloaderService
import com.pietrantuono.podcasts.repository.SaveAndDowloandEpisodeIntentService
import dagger.Subcomponent

@DownloaderServiceScope
@Subcomponent(modules = arrayOf(DownloadModule::class))
interface DownloadSubComponent {
    fun inject(downloaderService: DownloaderService)
    fun inject(downloaderService: SaveAndDowloandEpisodeIntentService)
}