package com.pietrantuono.podcasts.player.player.service.di

import com.pietrantuono.podcasts.player.player.service.MusicService
import dagger.Subcomponent

@ServiceScope
@Subcomponent(modules = arrayOf(ServiceModule::class))
interface ServiceComponent {

    fun inject(playerService: MusicService)
}