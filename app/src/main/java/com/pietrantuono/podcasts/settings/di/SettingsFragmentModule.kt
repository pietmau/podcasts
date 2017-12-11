package com.pietrantuono.podcasts.settings.di

import android.content.Context
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloader.di.SLACK_IN_BYTES
import com.pietrantuono.podcasts.downloader.downloader.DirectoryProvider
import com.pietrantuono.podcasts.downloader.downloader.DirectoryProviderImpl
import com.pietrantuono.podcasts.settings.PreferencesManager
import com.pietrantuono.podcasts.settings.presenter.SettingsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SettingsFragmentModule {

    @Provides
    fun providepresenter(provider: ResourcesProvider, directories: DirectoryProvider, preferencces: PreferencesManager) = SettingsPresenter(provider, directories, preferencces)

    @Provides
    fun provideDirectoryProvider(preferences: PreferencesManager, @Named(SLACK_IN_BYTES) slack: Int, context: Context): DirectoryProvider {
        return DirectoryProviderImpl(context, preferences, slack)
    }

    @Provides
    @Named(SLACK_IN_BYTES)
    fun provideSlackInBytes(): Int = 2 * 1024 * 1024

}