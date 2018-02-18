package com.pietrantuono.podcasts.musicservice

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MusicServiceModule::class))
interface MusicServiceComponent {

    fun inject(service: com.pietrantuono.podcasts.musicservice.MusicService)
}