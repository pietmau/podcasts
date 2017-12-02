package com.pietrantuono.podcasts.player

import android.content.Context
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository
import javax.inject.Singleton

@Module
class PlayerModule {

    @Singleton
    @Provides
    fun provideEnqueuer(context: Context, repo: EpisodesRepository): Enqueuer = EnqueuerImpl(context, repo)

}