package com.pietrantuono.podcasts.main.dagger

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.main.presenter.MainPresenter

import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    internal fun provideMainPresenter(checker: ApiLevelChecker): MainPresenter {
        return MainPresenter(checker)
    }
}
