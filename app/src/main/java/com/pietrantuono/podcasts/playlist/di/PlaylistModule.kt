package com.pietrantuono.podcasts.playlist.di

import com.pietrantuono.podcasts.playlist.presenter.PlaylistPresenter
import dagger.Module
import dagger.Provides

@Module
class PlaylistModule {

    @Provides
    fun providePresenter() = PlaylistPresenter()
}