package com.pietrantuono.podcasts.downloader.di

import android.content.Context
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloader.downloader.FetchDownloader
import com.pietrantuono.podcasts.downloader.downloader.RequestGenerator
import com.pietrantuono.podcasts.downloader.downloader.RequestGeneratorImpl
import com.pietrantuono.podcasts.downloader.service.Notificator
import com.pietrantuono.podcasts.downloader.service.NotificatorImpl
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

