package com.pietrantuono.podcasts.settings

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun providePreferencesManager(context: Context): PreferencesManager {
        return PreferencesManagerImpl(context)
    }
}

