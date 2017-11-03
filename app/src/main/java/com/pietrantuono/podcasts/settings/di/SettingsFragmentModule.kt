package com.pietrantuono.podcasts.settings.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloader.downloader.DirectoryProvider
import com.pietrantuono.podcasts.settings.PreferencesManager
import com.pietrantuono.podcasts.settings.presenter.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class SettingsFragmentModule {

    @Provides
    fun providepresenter(provider: ResourcesProvider, directories: DirectoryProvider, preferencces: PreferencesManager) = SettingsPresenter(provider, directories, preferencces)
}