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
import javax.inject.Named
import javax.inject.Singleton

private const val SLACK_IN_BYTES = "slack_in_bytes"

@Singleton
@Module
class DownloadModule() {

    @Singleton
    @Provides
    fun provideFetcher(provider: DirectoryProvider, repo: EpisodesRepository, manager: RequestManager, completemanager: CompletedDownloadsManager, context: Context): Fetcher {
        return FetcherImpl(context, repo, manager, completemanager)
    }

    @Provides
    fun provideNotificator(repo: EpisodesRepository, logger: DebugLogger, context: Context): DownloadNotificator {
        return DownloadNotificatorImpl(context, repo, logger)
    }

    @Provides
    fun provideRequestGenerator(provider: DirectoryProvider, repository: EpisodesRepository): RequestManager {
        return RequestManagerImpl(provider, repository)
    }

    @Provides
    fun provideDirectoryProvider(preferences: PreferencesManager, @Named(SLACK_IN_BYTES) slack: Int, context: Context): DirectoryProvider {
        return DirectoryProviderImpl(context, preferences, slack)
    }

    @Provides
    @Named(SLACK_IN_BYTES)
    fun provideSlackInBytes(): Int = 1 * 1024 * 1024

    @Provides
    fun provideNetworkDetector(preferencesManager: PreferencesManager, provider: DirectoryProvider, context: Context)
            = NetworkDiskAndPreferenceManager(context, preferencesManager, provider)

    @Provides
    fun provideDownloadManager(episodeRepo: EpisodesRepository, podcastRepo: PodcastRepo)
            = CompletedDownloadsManager(episodeRepo, podcastRepo)
}

