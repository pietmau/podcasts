package com.pietrantuono.podcasts.main.dagger

import com.pietrantuono.podcasts.main.presenter.MainPresenter

import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    internal fun provideMainPresenter(): MainPresenter {
        return MainPresenter()
    }
}
