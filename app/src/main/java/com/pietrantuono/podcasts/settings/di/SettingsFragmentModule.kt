package com.pietrantuono.podcasts.settings.di

import com.pietrantuono.podcasts.settings.presenter.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class SettingsFragmentModule {

    @Provides
    fun providepresenter() = SettingsPresenter(manager)
}