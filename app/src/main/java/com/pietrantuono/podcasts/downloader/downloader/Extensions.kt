package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo
import hugo.weaving.DebugLog


internal fun Fetch.shutDown() {
    removeRequests()
    removeFetchListeners()
    release()
}


internal fun Fetch.alreadyDownloaded(url: String): Boolean =
        get().filter { it.url.equals(url, true) }.size > 0

@DebugLog
internal fun Fetch.enqueueRequest(request: Request): Pair<Long, RequestInfo?> {
    val id = enqueue(request)
    return Pair(id, get(id))
}

internal fun Fetch.removeAll(ids: List<Long>) {
    ids.forEach { remove(it) }
}
