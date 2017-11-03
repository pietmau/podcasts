package com.pietrantuono.podcasts.settings.presenter

import android.support.v7.preference.Preference
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloader.downloader.DirectoryProvider


class SettingsPresenter(
        private val resources: ResourcesProvider,
        private val directories: DirectoryProvider) {

    fun setSdCardPreferenceText(sdPreference: Preference?) {
        if (sdPreference == null) {
            return
        }
        if (!directories.externalStorageIsAvailable) {
            externalStorageNotAvailable(sdPreference)
            return
        }
        sdPreference.summary = directories.downloadDir
    }

    private fun externalStorageNotAvailable(sdPreference: Preference) {
        sdPreference.summary = resources.getString(R.string.external_storage_unavailable)
    }

}