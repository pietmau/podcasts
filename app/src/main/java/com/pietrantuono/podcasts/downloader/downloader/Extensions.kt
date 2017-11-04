package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.Fetch


internal fun Fetch.shutDown() {
    removeRequests()
    removeFetchListeners()
    release()
}


internal fun Fetch.alreadyDownloaded(url: String): Boolean =
        get().filter { info -> info.url.equals(url, true) }.size > 0
