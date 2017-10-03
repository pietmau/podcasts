package com.pietrantuono.podcasts.player.player.service.di

import android.content.Context
import com.pietrantuono.podcasts.player.player.service.provider.MusicProviderSource
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModel
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModelImpl
import com.pietrantuono.podcasts.player.player.service.playback.LocalPlayback
import com.pietrantuono.podcasts.player.player.service.playback.Playback
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProvider
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProviderImpl
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides


@ServiceScope
@Module
class ServiceModule {

    @Provides
    fun providesModel(repo: EpisodesRepository): PlayerServiceModel = PlayerServiceModelImpl(repo)

    @ServiceScope
    @Provides
    fun provideMusicProviderSource() = MusicProviderSource()

    @ServiceScope
    @Provides
    fun providePodcastProvider(repository: EpisodesRepository): PodcastProvider = PodcastProviderImpl(repository)

    @ServiceScope
    @Provides
    fun provideLocalPlayback(context: Context, provider: PodcastProvider): Playback = LocalPlayback(context, provider)

}

