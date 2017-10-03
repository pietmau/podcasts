package com.pietrantuono.podcasts.player.player.service.di

import android.content.Context
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModel
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModelImpl
import com.pietrantuono.podcasts.player.player.service.playback.CustomLocalPlayback
import com.pietrantuono.podcasts.player.player.service.playback.Playback
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProvider
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProviderImpl
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    fun providesModel(repo: EpisodesRepository): PlayerServiceModel = PlayerServiceModelImpl(repo)

    @Provides
    fun provideMusicProviderSource() = CustomMusicProviderSource()

    @Provides
    fun providePodcastProvider(repository: EpisodesRepository): PodcastProvider = PodcastProviderImpl(repository)

    @Provides
    fun provideLocalPlayback(context: Context, provider: PodcastProvider): Playback = CustomLocalPlayback(context, provider)

}

