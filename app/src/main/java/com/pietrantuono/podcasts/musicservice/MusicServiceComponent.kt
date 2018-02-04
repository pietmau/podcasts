package com.pietrantuono.podcasts.musicservice

import dagger.Subcomponent

@MusicServiceScope
@Subcomponent(modules = arrayOf(MusicServiceModule::class))
interface MusicServiceComponent {

    fun inject(service: com.pietrantuono.podcasts.musicservice.MusicService)
}