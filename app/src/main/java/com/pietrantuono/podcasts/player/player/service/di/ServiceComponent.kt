package com.pietrantuono.podcasts.player.player.service.di

import com.pietrantuono.podcasts.player.player.service.CustomMusicService
import com.pietrantuono.podcasts.player.player.service.PlayerService
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ServiceModule::class))
interface ServiceComponent {

    fun inject(playerService: PlayerService)
    fun inject(playerService: CustomMusicService)
}