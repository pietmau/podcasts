package com.pietrantuono.podcasts.player

import com.tonyodev.fetch.request.RequestInfo


interface Enqueuer {
    fun addToQueueIfAppropriate(intent: RequestInfo)
    fun storeRequestIfAppropriate(second: RequestInfo?, stringExtra: Boolean)
}