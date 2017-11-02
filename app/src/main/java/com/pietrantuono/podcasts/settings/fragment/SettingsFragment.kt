package com.pietrantuono.podcasts.settings.fragment


import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.settings.di.SettingsFragmentModule
import com.pietrantuono.podcasts.settings.presenter.SettingsPresenter
import javax.inject.Inject


class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
        val TAG = "SettingsFragment"
    }

    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        (activity.application as? App)?.applicationComponent?.with(SettingsFragmentModule())?.inject(this)
        setSdCardPreferenceText()
    }

    private fun setSdCardPreferenceText() {
        val sdPreference = findPreference(context.getString(R.string.external_storage))
        presenter.setSdCardPreferenceText(sdPreference)
    }


}