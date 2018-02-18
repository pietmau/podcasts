package com.pietrantuono.podcasts.repository

import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import dagger.Module
import dagger.Provides
import io.realm.Realm
import repository.*
import javax.inject.Singleton

@Singleton
@Module
class RepositoryModule {

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun providePodcastRepo(realm: Realm, services: RepoServices, logger: DebugLogger): PodcastRepo {
        return PodcastRepoRealm(services)
    }

    @Provides
    fun provideServices(context: Context, realm: Realm, logger: DebugLogger): RepoServices {
        return RepoServicesImpl(context, logger)
    }

    @Singleton
    @Provides
    fun provideEpisodesRepository(): EpisodesRepository = EpisodesRepositoryRealm()

}

