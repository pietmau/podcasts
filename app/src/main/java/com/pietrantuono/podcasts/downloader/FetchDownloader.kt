package com.pietrantuono.podcasts.downloader

import android.content.Context
import com.tonyodev.fetch.Fetch

class FetchDownloader(context: Context) : Dowloader {
    private val fetch: Fetch

    init {
        fetch = Fetch.newInstance(context)
    }

}