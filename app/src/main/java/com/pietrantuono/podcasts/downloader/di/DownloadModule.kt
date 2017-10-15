package com.pietrantuono.podcasts.downloader.di

import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.DownloadNotificator
import com.pietrantuono.podcasts.downloader.service.DownloadNotificatorImpl
import com.pietrantuono.podcasts.downloader.service.NetworkDetector
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides


@DownloaderServiceScope
@Module
class DownloadModule(private val context: Context) {

    @DownloaderServiceScope
    @Provides
    fun provideFetcher(provider: DirectoryProvider, repo: EpisodesRepository, manager: RequestManager): Fetcher {
        return FetcherImpl(context, provider, repo, manager)
    }

    @Provides
    fun provideNotificator(repo: EpisodesRepository, logger: DebugLogger): DownloadNotificator {
        return DownloadNotificatorImpl(context, repo, logger)
    }

    @Provides
    fun provideRequestGenerator(provider: DirectoryProvider, repository: EpisodesRepository): RequestManager {
        return RequestManagerImpl(provider, repository)
    }

    @Provides
    fun provideDirectoryProvider(): DirectoryProvider {
        return DirectoryProviderImpl(context)
    }

    @Provides
    fun provideNetworkDetector(preferencesManager: PreferencesManager) = NetworkDetector(context, preferencesManager)
}

