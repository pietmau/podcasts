package com.pietrantuono.podcasts.downloader

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DownloadModule(private val context: Context) {

    @Provides
    fun provideDowloader(): Downloader {
        return FetchDownloader(context)
    }

    @Provides
    fun provideNotificator(): Notificator {
        return NotificatorImpl()
    }

    @Provides
    fun provideRequestGenerator(): RequestGenerator {
        return RequestGeneratorImpl()
    }

}

