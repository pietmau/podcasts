package com.pietrantuono.podcasts.settings.presenter

import android.support.v7.preference.Preference
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.settings.PreferencesManager


class SettingsPresenter(
        private val preferenceManager: PreferencesManager,
        private val resources: ResourcesProvider) {

    fun setSdCardPreferenceText(sdPreference: Preference?) {
        if (preferenceManager.saveOnSdCard) {
        } else {
        }
    }
}