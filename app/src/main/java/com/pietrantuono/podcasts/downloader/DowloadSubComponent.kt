package com.pietrantuono.podcasts.downloader

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(DownloadModule::class))
interface DowloadSubComponent {
    fun inject(dowloaderService: DowloaderService)
}