package com.pietrantuono.podcasts.addpodcast.repository

import com.pietrantuono.podcasts.addpodcast.repository.repository.PodcastRepo
import com.pietrantuono.podcasts.addpodcast.repository.repository.PodcastRepoRealm
import com.pietrantuono.podcasts.addpodcast.repository.repository.RealmRepository
import com.pietrantuono.podcasts.addpodcast.repository.repository.Repository
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(realm: Realm, podcastRepo: PodcastRepo): Repository {
        return RealmRepository(realm, podcastRepo)
    }

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
    
    @Provides
    fun providePodcastRepo(realm: Realm): PodcastRepo {
        return PodcastRepoRealm(realm)
    }
}