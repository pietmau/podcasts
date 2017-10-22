package com.pietrantuono.podcasts.repository

import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.pietrantuono.podcasts.repository.repository.PodcastRepoRealm
import com.pietrantuono.podcasts.repository.repository.RepoServices
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Singleton
@Module
class RepositoryModule {

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun providePodcastRepo(realm: Realm, services: RepoServices): PodcastRepo {
        return PodcastRepoRealm(services)
    }

    @Provides
    fun provideServices(context: Context, realm: Realm, logger: DebugLogger): RepoServices {
        return RepoServicesImpl(context, logger)
    }

    @Singleton
    @Provides
    fun provideEpisodesRepository(realm: Realm, cache: EpisodeCache): EpisodesRepository {
        return EpisodesRepositoryRealm(cache)
    }

    @Singleton
    @Provides
    fun provideCache(): EpisodeCache = EpisodeCacheImpl()

}

