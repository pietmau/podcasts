package com.pietrantuono.podcasts.fullscreenplay.di

import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.main.view.TransitionsFramework
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
    fun provideTransitionImageLoadingListener(framework: TransitionsFramework): TransitionImageLoadingListener{
        return TransitionImageLoadingListener(framework)
    }
}