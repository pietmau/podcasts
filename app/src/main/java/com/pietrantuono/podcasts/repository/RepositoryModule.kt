package com.pietrantuono.podcasts.repository

import android.content.Context
import com.pietrantuono.podcasts.repository.repository.*
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(realm: Realm, podcastRepo: PodcastRepo): Repository {
        return RealmRepository(realm, podcastRepo)
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun providePodcastRepo(realm: Realm, services: RepoServices): PodcastRepo {
        return PodcastRepoRealm(realm, services)
    }

    @Provides
    fun provideServices(context: Context, realm: Realm): RepoServices {
        return RepoServicesImpl(context, realm)
    }

    @Provides
    fun provideEpisodesRepository(realm: Realm): EpisodesRepository {
        return EpisodesRepositoryRealm(realm)
    }

}

