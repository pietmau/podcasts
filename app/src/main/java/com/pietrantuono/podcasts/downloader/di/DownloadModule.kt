package com.pietrantuono.podcasts.downloader.di

import android.app.NotificationManager
import android.content.Context
import android.support.v4.content.LocalBroadcastManager
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.*
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository
import repo.repository.PodcastRepo
import javax.inject.Named
import javax.inject.Singleton

private const val SLACK_IN_BYTES = "slack_in_bytes"

@Singleton
@Module
class DownloadModule() {

    @Singleton
    @Provides
    fun provideFetcher(provider: DirectoryProvider, fetcherModel: FetcherModelImpl, manager: RequestManager, completemanager: CompletedDownloadsManager, context: Context): Fetcher {
        return FetcherImpl(context, fetcherModel, manager, completemanager)
    }

    @Provides
    fun provideNotificator(repo: EpisodesRepository, logger: DebugLogger, context: Context, creator: DownloadNotificationCreator, communicator: Communicator): DownloadNotificator =
            DownloadNotificatorImpl(repo, logger, creator, communicator)

    @Provides
    fun provideRequestGenerator(provider: DirectoryProvider, repository: EpisodesRepository): RequestManager {
        return RequestManagerImpl(provider, repository)
    }

    @Provides
    fun provideCommunicator(context: Context): Communicator {
        val notifmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val localBrodcastManager = LocalBroadcastManager.getInstance(context)
        return CommunicatorImpl(notifmanager, localBrodcastManager)
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

