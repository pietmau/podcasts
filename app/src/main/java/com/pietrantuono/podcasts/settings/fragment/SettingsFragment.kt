package com.pietrantuono.podcasts.settings.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.settings.di.SettingsFragmentModule
import com.pietrantuono.podcasts.settings.presenter.SettingsPresenter
import com.pietrantuono.podcasts.settings.presenter.SettingsView
import javax.inject.Inject


class SettingsFragment : PreferenceFragmentCompat(), SettingsView {
    companion object {
        val TAG = "SettingsFragment"
        fun navigateTo(fragmentManager: FragmentManager) {
            val frag: Fragment = fragmentManager.findFragmentByTag(SettingsFragment.TAG) ?: SettingsFragment()
            fragmentManager.beginTransaction()?.replace(R.id.fragmentContainer, frag, SettingsFragment.TAG)?.commit()
        }
    }

    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        (activity.application as? App)?.appComponent?.with(SettingsFragmentModule())?.inject(this)
        presenter.bindView(this)
    }

    override fun setSdCardPreferenceText(summary: String) {
        val sdPreference = findPreference(context.getString(R.string.external_storage))
        sdPreference?.summary = summary
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.setTitle(R.string.settings)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }
}