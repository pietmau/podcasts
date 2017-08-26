package com.pietrantuono.podcasts.fullscreenplay.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.main.view.Transitions
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides


@Module
class FullscreenModule() {

    @Provides
    fun provideFullscreenPresenter(repo: EpisodesRepository, player: Player?): FullscreenPresenter {
        return FullscreenPresenter(repo, player)
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: Transitions): BitmapColorExtractor {
        return BitmapColorExtractor()
    }
}