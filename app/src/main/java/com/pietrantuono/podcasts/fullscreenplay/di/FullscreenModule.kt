package com.pietrantuono.podcasts.fullscreenplay.di

import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import dagger.Module
import dagger.Provides


@Module
class FullscreenModule {

    @Provides
    fun provideFullscreenPresenter(): FullscreenPresenter = FullscreenPresenter()
}