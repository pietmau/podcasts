package com.pietrantuono.podcasts.settings.di

import com.pietrantuono.podcasts.settings.fragment.SettingsFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(SettingsFragmentModule::class))
interface SettingsFragmentComponent {

    fun inject(settingsFragment: SettingsFragment)
}