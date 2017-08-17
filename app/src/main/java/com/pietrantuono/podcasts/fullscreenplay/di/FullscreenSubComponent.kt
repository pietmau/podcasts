package com.pietrantuono.podcasts.fullscreenplay.di

import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FullscreenModule::class))
interface FullscreenSubComponent {
    fun inject(fullscreenPlayActivity: FullscreenPlayActivity)
}