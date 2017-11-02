package com.pietrantuono.podcasts.settings.di

import android.content.Context
import com.pietrantuono.podcasts.settings.PreferencesManager
import com.pietrantuono.podcasts.settings.PreferencesManagerImpl
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun providePreferencesManager(context: Context): PreferencesManager {
        return PreferencesManagerImpl(context)
    }
}

