package com.pietrantuono.podcasts.repository

import android.arch.persistence.room.Room
import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.repository.repository.AppDatabase
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.pietrantuono.podcasts.repository.repository.RepoServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class RepositoryModule {

    @Provides
    fun providePodcastRepo(dao: PodcastDao): PodcastRepo = PodcastRepoRoom(dao)

    @Provides
    fun provideServices(context: Context, logger: DebugLogger): RepoServices {
        return RepoServicesImpl(context, logger)
    }

    @Provides
    fun providePodcastDao(context: Context): PodcastDao =
            Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.PODCAST_DATABASE).build().podcastDao()

    @Singleton
    @Provides
    fun provideEpisodesRepository(cache: EpisodeCache): EpisodesRepository {
        return EpisodesRepositoryRoom(cache)
    }

    @Singleton
    @Provides
    fun provideCache(): EpisodeCache = EpisodeCacheImpl()

}

