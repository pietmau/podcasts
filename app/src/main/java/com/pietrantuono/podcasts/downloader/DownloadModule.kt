package com.pietrantuono.podcasts.downloader

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DownloadModule(private val context: Context) {

    @Provides
    fun provideDowloader(): Dowloader {
        return FetchDownloader(context)
    }
}

