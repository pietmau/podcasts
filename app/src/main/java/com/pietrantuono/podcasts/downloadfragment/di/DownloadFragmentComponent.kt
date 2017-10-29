package com.pietrantuono.podcasts.downloadfragment.di

import com.pietrantuono.podcasts.downloadfragment.view.DownloadFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(DownloadFragmentModule::class))
interface DownloadFragmentComponent {
    fun inject(downloadFragment: DownloadFragment)
}