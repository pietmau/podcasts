package com.pietrantuono.podcasts.fullscreenplay.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModelImpl
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.fullscreenplay.presenter.ServiceConnector
import com.pietrantuono.podcasts.main.view.Transitions
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers

@Module
class FullscreenModule() {

    @Provides
    fun provideFullscreenPresenter(model: FullscreenModel, player: Player?): FullscreenPresenter {
        return FullscreenPresenter(model, player, ServiceConnector())
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: Transitions): BitmapColorExtractor {
        return BitmapColorExtractor()
    }

    @Provides
    fun provideModel(repository: EpisodesRepository): FullscreenModel {
        return FullscreenModelImpl(repository, AndroidSchedulers.mainThread())
    }
}