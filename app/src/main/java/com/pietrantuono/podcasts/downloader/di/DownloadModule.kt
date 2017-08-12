package com.pietrantuono.podcasts.downloader.di

import android.content.Context
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.Notificator
import com.pietrantuono.podcasts.downloader.service.NotificatorImpl
import com.pietrantuono.podcasts.settings.PreferencesManager
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
    fun provideRequestGenerator(preferenceManager: PreferencesManager, provider: DirectoryProvider): RequestGenerator {
        return RequestGeneratorImpl(provider)
    }

    @Provides
    fun provideDirectoryProvider():DirectoryProvider{
        return DirectoryProviderImpl(context)
    }
}

