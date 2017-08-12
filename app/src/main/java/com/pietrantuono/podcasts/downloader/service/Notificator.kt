package com.pietrantuono.podcasts.downloader.service

import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

interface Notificator {
    fun notifyprogress(request: Request?, info: RequestInfo?, id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int)

}