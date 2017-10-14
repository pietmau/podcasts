package com.pietrantuono.podcasts.downloader.service

import com.tonyodev.fetch.request.RequestInfo

interface DownloadNotificator {
    fun notifyProgress(request: RequestInfo?, progress: Int)
}