package com.pietrantuono.podcasts.settings

import android.content.SharedPreferences
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider


class PreferencesManagerImpl(
        private val sharedPref: SharedPreferences,
        private val resourcesProvider: ResourcesProvider) : PreferencesManager {

    override val saveOnSdCard: Boolean
        get() = sharedPref.getBoolean(resourcesProvider.getString(R.string.external_storage), false)

    override val listenOnMobileNetwork: Boolean
        get() = !sharedPref.getBoolean(resourcesProvider.getString(R.string.listen_wifi), false)

    override val downloadOnMobileNetwork: Boolean
        get() = !sharedPref.getBoolean(resourcesProvider.getString(R.string.download_wifi), false)

    override fun registerListener(listener: (sharedPreferences: SharedPreferences, key: String) -> Unit) {
        sharedPref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unRegisterListener(listener: (sharedPreferences: SharedPreferences, key: String) -> Unit) {
        sharedPref.unregisterOnSharedPreferenceChangeListener(listener)
    }
}