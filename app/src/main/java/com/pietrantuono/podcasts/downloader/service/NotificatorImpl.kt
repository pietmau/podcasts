package com.pietrantuono.podcasts.downloader.service

import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

class NotificatorImpl : Notificator {
    override fun notifyUser(request: Request?, info: RequestInfo?, id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {

    }
}