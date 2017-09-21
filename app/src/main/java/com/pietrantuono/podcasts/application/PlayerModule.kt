package com.pietrantuono.podcasts.application

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import com.pietrantuono.podcasts.player.player.playback.LocalPlayback
import com.pietrantuono.podcasts.player.player.playback.Playback
import com.pietrantuono.podcasts.player.player.playback.PlaybackStateCreator
import dagger.Module
import dagger.Provides

@Module
class PlayerModule {

    @Provides
    fun providesPlaybackStateCreator() = PlaybackStateCreator()

    @Provides
    fun providesPlayback(context: Context, exoplayer: SimpleExoPlayer, creator: PlaybackStateCreator): Playback = LocalPlayback(context, exoplayer, creator)
}