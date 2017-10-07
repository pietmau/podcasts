package com.pietrantuono.podcasts.fullscreenplay.di

import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.CustomControlsImpl
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FullscreenModule::class))
interface FullscreenSubComponent {

    fun inject(fullscreenPlayActivity: FullscreenPlayActivity)

    fun inject(fullscreenPlayActivity: CustomControlsImpl)
}