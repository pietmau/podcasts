package com.pietrantuono.podcasts.downloader.di

import android.content.Context
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.Notificator
import com.pietrantuono.podcasts.downloader.service.NotificatorImpl
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides

@Module
class DownloadModule(private val context: Context) {

    @Provides
    fun provideDowloader(provider: DirectoryProvider): Downloader {
        return FetchDownloader(context, provider)
    }

    @Provides
    fun provideNotificator(): Notificator {
        return NotificatorImpl(context)
    }

    @Provides
    fun provideRequestGenerator(preferenceManager: PreferencesManager, provider: DirectoryProvider, repository: EpisodesRepository): RequestGenerator {
        return RequestGeneratorImpl(provider, repository)
    }

    @Provides
    fun provideDirectoryProvider():DirectoryProvider{
        return DirectoryProviderImpl(context)
    }
}

