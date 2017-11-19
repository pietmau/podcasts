package com.pietrantuono.podcasts.player.player.service.di

import dagger.Subcomponent

@ServiceScope
@Subcomponent(modules = arrayOf(ServiceModule::class))
interface ServiceComponent {

    fun inject(playerService: MusicService)
}