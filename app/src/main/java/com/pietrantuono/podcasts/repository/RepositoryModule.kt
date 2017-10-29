package com.pietrantuono.podcasts.repository

import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.pietrantuono.podcasts.repository.repository.RepoServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class RepositoryModule {

    @Provides
    fun providePodcastRepo(): PodcastRepo {
        return PodcastRepoRoom()
    }

    @Provides
    fun provideServices(context: Context, logger: DebugLogger): RepoServices {
        return RepoServicesImpl(context, logger)
    }

    @Singleton
    @Provides
    fun provideEpisodesRepository(cache: EpisodeCache): EpisodesRepository {
        return EpisodesRepositoryRoom(cache)
    }

    @Singleton
    @Provides
    fun provideCache(): EpisodeCache = EpisodeCacheImpl()

}

