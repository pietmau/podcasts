package com.pietrantuono.podcasts.downloader.di

import android.content.Context
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.DownloadNotificator
import com.pietrantuono.podcasts.downloader.service.DownloadNotificatorImpl
import com.pietrantuono.podcasts.downloader.service.NetworkDetector
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides

@Module
class DownloadModule(private val context: Context) {

    @Provides
    fun provideFetcher(provider: DirectoryProvider): Fetcher {
        return FetcherImpl(context, provider)
    }

    @Provides
    fun provideNotificator(): DownloadNotificator {
        return DownloadNotificatorImpl(context)
    }

    @Provides
    fun provideRequestGenerator(provider: DirectoryProvider, repository: EpisodesRepository): RequestGenerator {
        return RequestGeneratorImpl(provider, repository)
    }

    @Provides
    fun provideDirectoryProvider(): DirectoryProvider {
        return DirectoryProviderImpl(context)
    }

    @Provides
    fun provideNetworkDetector(preferencesManager: PreferencesManager) = NetworkDetector(context, preferencesManager)
}

