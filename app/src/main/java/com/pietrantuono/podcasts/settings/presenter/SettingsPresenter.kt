package com.pietrantuono.podcasts.settings.presenter

import android.content.SharedPreferences
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloader.downloader.DirectoryProvider
import com.pietrantuono.podcasts.settings.PreferencesManager


class SettingsPresenter(
        private val resources: ResourcesProvider,
        private val directories: DirectoryProvider,
        private val preferencesManager: PreferencesManager) {

    private lateinit var settingsView: SettingsView

    private val listener: (sharedPreferences: SharedPreferences, key: String) -> Unit =
            { sharedPreferences, key ->setSdCardPreferenceText() }

    private fun setSdCardPreferenceText() {
        if (!directories.externalStorageIsAvailable) {
            settingsView.setSdCardPreferenceText(resources.getString(R.string.external_storage_unavailable))
            return
        }
        settingsView.setSdCardPreferenceText(directories.downloadDir)
    }

    fun bindView(settingsView: SettingsView) {
        this.settingsView = settingsView
    }

    fun onResume() {
        preferencesManager.registerListener(listener)
        setSdCardPreferenceText()
    }

    fun onPause() {
        preferencesManager.unRegisterListener(listener)
    }

}