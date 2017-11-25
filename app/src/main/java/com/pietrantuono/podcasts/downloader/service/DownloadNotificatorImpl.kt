package com.pietrantuono.podcasts.downloader.service

import android.app.Notification
import android.app.Service
import com.pietrantuono.podcasts.application.DebugLogger
import com.tonyodev.fetch.request.RequestInfo
import pojos.Episode
import repo.repository.EpisodesRepository

class DownloadNotificatorImpl(
        private val repo: EpisodesRepository,
        private val debugger: DebugLogger,
        private val notificationCreator: DownloadNotificationCreator,
        private val notifManager: Communicator
) : DownloadNotificator {

    val DOWNLOAD_COMPLETED: Int = 100
    private val TAG: String? = "DownloadNotificatorImpl"
    private val GENERIC_NOTIFICATION = 1

    override fun notifyProgress(service: Service, requestInfo: RequestInfo?, progress: Int) {
        val url = requestInfo?.url
        url ?: return //TODO nice!!
        repo.getEpisodeByEnclosureUrlSync(url)?.let {
            val id = requestInfo.id.toInt()
            service.startForeground(id, getNotification(it, progress))
        }
    }

    private fun getNotification(episode: Episode, progress: Int): Notification? =
            if (progress < DOWNLOAD_COMPLETED) {
                notificationCreator.createProgressNotification(episode, progress)
            } else {
                notificationCreator.createCompleteNotification(episode)
            }

    override fun notifySpaceUnavailable(requestInfo: RequestInfo) {
        val url = requestInfo.url
        repo.getEpisodeByEnclosureUrlSync(url)?.let {
            val id = requestInfo.id.toInt()
            notifManager.notify(id, notificationCreator.getNoSpaceNotification(it))
        }
    }

    override fun notifySpaceUnavailable(url: String) {
        repo.getEpisodeByUrlSync(url)?.let {
            val noSpaceNotification = notificationCreator.getNoSpaceNotification(it)
            debugger.debug(TAG, "notify")
            notifManager.notify(GENERIC_NOTIFICATION, noSpaceNotification)
        }
    }

    override fun broadcastUpdate(info: RequestInfo, progress: Int, fileSize: Long) {
        notifManager.sendBoroadcast(notificationCreator.makeUpdateBroadcast(info, progress, fileSize))
    }

    override fun broadcastOnDownloadCompleted(requestInfo: RequestInfo) {
        notifManager.sendBoroadcast(notificationCreator.makeOnDownloadCompletedBroadcast(requestInfo))
    }
    override fun broadcastDeleteEpisode(id: Long) {
        repo.getEpisodeByDownloadIdSync(id)?.let {
            val intent = notificationCreator.makeOnDeleteEpisode(it)
            notifManager.sendBoroadcast(intent)
        }
    }

}