package com.pietrantuono.podcasts.playlist.di

import com.pietrantuono.podcasts.fullscreenplay.customcontrols.MediaBrowserCompatWrapper
import com.pietrantuono.podcasts.playlist.model.PlaylistModel
import com.pietrantuono.podcasts.playlist.model.PlaylistModelImpl
import com.pietrantuono.podcasts.playlist.presenter.PlaylistPresenter
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository

@Module
class PlaylistModule {

    @Provides
    fun providePresenter(wrapper: MediaBrowserCompatWrapper, mapper: PlaylistModel) = PlaylistPresenter(wrapper, mapper)

    @Provides
    fun provideMediaItemMapper(repo: EpisodesRepository): PlaylistModel = PlaylistModelImpl(repo)
}