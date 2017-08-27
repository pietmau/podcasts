package com.pietrantuono.podcasts.fullscreenplay.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.fullscreenplay.ServiceConnectionManager
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.main.view.Transitions
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides


@Module
class FullscreenModule() {

    @Provides
    fun provideFullscreenPresenter(repo: EpisodesRepository, player: Player?, creator: MediaSourceCreator): FullscreenPresenter {
        return FullscreenPresenter(repo, player, creator)
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: Transitions): BitmapColorExtractor {
        return BitmapColorExtractor()
    }

    @Provides
    fun provideServiceConnectionManager(): ServiceConnectionManager {
        return ServiceConnectionManager()
    }
}