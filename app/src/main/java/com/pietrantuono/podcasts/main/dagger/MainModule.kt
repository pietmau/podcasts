package com.pietrantuono.podcasts.main.dagger

import android.content.Context
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.main.presenter.KillSwitch
import com.pietrantuono.podcasts.main.presenter.MainPresenter

import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    internal fun provideMainPresenter(checker: ApiLevelChecker, killSwitch: KillSwitch): MainPresenter = MainPresenter(checker, killSwitch)

    @Provides
    internal fun provideKillSwitch(context: Context) = KillSwitch(context.packageManager)
}
