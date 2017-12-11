package com.pietrantuono.podcasts.downloader.di

import android.app.NotificationManager
import android.content.Context
import android.support.v4.content.LocalBroadcastManager
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.*
import com.pietrantuono.podcasts.downloader.service.*
import com.pietrantuono.podcasts.settings.PreferencesManager
import com.tonyodev.fetch.Fetch
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository
import repo.repository.PodcastRepo
import javax.inject.Named

const val SLACK_IN_BYTES = "slack_in_bytes"

@DownloadScope
@Module
class DownloadModule(private val context: Context) {
    private var fetch: Fetch? = null

    @DownloadScope
    @Provides
    fun provideFetcher(fetcherModel: FetcherModelImpl, manager: RequestManager, completemanager: CompletedDownloadsManager, fetch: Fetch, debuglogger: DebugLogger): Fetcher {
        return FetcherImpl(fetch, fetcherModel, manager, completemanager, debuglogger)
    }

    @Provides
    fun provideFetch(): Fetch {
        if (fetch == null || fetch?.isValid != true) {
            fetch = Fetch.newInstance(context)
            fetch?.setConcurrentDownloadsLimit(1)
        }
        return fetch!!
    }

    @Provides
    fun provideNotificator(repo: EpisodesRepository, creator: DownloadNotificationCreator, communicator: Communicator): DownloadNotificator =
            DownloadNotificatorImpl(repo, creator, communicator)

    @Provides
    fun provideRequestGenerator(provider: DirectoryProvider, repository: EpisodesRepository): RequestManager {
        return RequestManagerImpl(provider, repository)
    }

    @Provides
    fun provideCommunicator(): Communicator {
        val notifmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val localBrodcastManager = LocalBroadcastManager.getInstance(context)
        return CommunicatorImpl(notifmanager, localBrodcastManager)
    }

    @Provides
    fun provideDirectoryProvider(preferences: PreferencesManager, @Named(SLACK_IN_BYTES) slack: Int): DirectoryProvider {
        return DirectoryProviderImpl(context, preferences, slack)
    }

    @Provides
    @Named(SLACK_IN_BYTES)
    fun provideSlackInBytes(): Int = 2 * 1024 * 1024

    @Provides
    fun provideNetworkDetector(preferencesManager: PreferencesManager, provider: DirectoryProvider)
            = NetworkDiskAndPreferenceManager(context, preferencesManager, provider)

    @Provides
    fun provideDownloadManager(episodeRepo: EpisodesRepository, podcastRepo: PodcastRepo) = CompletedDownloadsManager(episodeRepo, podcastRepo)

    @Provides
    fun provideDownloaerDeleter(fetcer: Fetcher, notificator: DownloadNotificator, networkDiskAndPreferenceManager: NetworkDiskAndPreferenceManager): DowloaderDeleter
            = DownloaderDeleterImpl(fetcer, notificator, networkDiskAndPreferenceManager)


}

