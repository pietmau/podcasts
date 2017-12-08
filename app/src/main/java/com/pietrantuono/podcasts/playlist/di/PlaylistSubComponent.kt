package com.pietrantuono.podcasts.playlist.di

import com.pietrantuono.podcasts.playlist.view.PlaylistFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(PlaylistModule::class))
interface PlaylistSubComponent {

    fun inject(playlistFragment: PlaylistFragment)
}