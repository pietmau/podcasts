package com.pietrantuono.podcasts.downloader

import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.service.DownloaderService
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(DownloadModule::class))
interface DownloadSubComponent {
    fun inject(downloaderService: DownloaderService)
}