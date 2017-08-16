package com.pietrantuono.podcasts.episode.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.EpisodeActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(EpisodeModule::class))
interface EpisodeSubComponent {

    fun inject(episodeActivity: EpisodeActivity)
}