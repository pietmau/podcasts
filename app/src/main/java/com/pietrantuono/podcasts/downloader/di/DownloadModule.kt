package com.pietrantuono.podcasts.downloader.di

import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.CompletedDownloadsManager
import com.pietrantuono.podcasts.downloader.service.DownloadNotificator
import com.pietrantuono.podcasts.downloader.service.DownloadNotificatorImpl
import com.pietrantuono.podcasts.downloader.service.NetworkDiskAndPreferenceManager
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides


@DownloaderServiceScope
@Module
class DownloadModule(private val context: Context) {

    @DownloaderServiceScope
    @Provides
    fun provideFetcher(provider: DirectoryProvider, repo: EpisodesRepository, manager: RequestManager, completemanager: CompletedDownloadsManager): Fetcher {
        return FetcherImpl(context, repo, manager, completemanager)
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
    fun provideDirectoryProvider(preferences: PreferencesManager): DirectoryProvider {
        return DirectoryProviderImpl(context, preferences)
    }

    @Provides
    fun provideNetworkDetector(preferencesManager: PreferencesManager, provider: DirectoryProvider)
            = NetworkDiskAndPreferenceManager(context, preferencesManager, provider)

    @Provides
    fun provideDownloadManager(episodeRepo: EpisodesRepository, podcastRepo: PodcastRepo)
            = CompletedDownloadsManager(episodeRepo, podcastRepo)
}

