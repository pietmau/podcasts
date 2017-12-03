package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import com.tonyodev.fetch.request.RequestInfo

interface DownloadNotificator {
    fun notifyStatus(service: Service, request: RequestInfo?, status: Int, progress: Int)
    fun notifySpaceUnavailable(requestInfo: RequestInfo)
    fun notifySpaceUnavailable(requestInfo: String)
    fun broadcastUpdate(info: RequestInfo, progress: Int, fileSize: Long)
    fun broadcastOnDownloadCompleted(requestInfo: RequestInfo)
    fun broadcastDeleteEpisode(id: Long)
}