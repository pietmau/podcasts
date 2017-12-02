package com.pietrantuono.podcasts.player

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlayerModule {

    @Singleton
    @Provides
    fun providePlayer(context: Context): SimplePlayer = SimplePlayerImpl(context)

}