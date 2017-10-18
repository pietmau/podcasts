package com.pietrantuono.podcasts.settings.fragment


import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.pietrantuono.podcasts.R


class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
        val TAG = "SettingsFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}