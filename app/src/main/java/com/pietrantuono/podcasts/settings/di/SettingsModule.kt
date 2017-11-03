package com.pietrantuono.podcasts.settings.di

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.settings.PreferencesManager
import com.pietrantuono.podcasts.settings.PreferencesManagerImpl
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun providePreferencesManager(prefs: SharedPreferences, resources: ResourcesProvider): PreferencesManager {
        return PreferencesManagerImpl(prefs, resources)
    }

    @Provides
    fun provideSharedPreferences(context: Context) =  PreferenceManager.getDefaultSharedPreferences(context)

}

