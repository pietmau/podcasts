package com.pietrantuono.podcasts.downloader.service

import android.app.Notification
import android.app.Service
import com.tonyodev.fetch.request.RequestInfo
import models.pojos.Episode
import repo.repository.EpisodesRepository

class DownloadNotificatorImpl(
        private val repo: EpisodesRepository,
        private val notificationCreator: DownloadNotificationCreator,
        private val notifManager: Communicator
) : DownloadNotificator {

    val STATUS_REMOVED = 905
    val DOWNLOAD_COMPLETED: Int = 100
    private val TAG: String? = "DownloadNotificatorImpl"
    private val GENERIC_NOTIFICATION = 1

    override fun notifyStatus(service: Service, requestInfo: RequestInfo?, status: Int, progress: Int) {
        val url = requestInfo?.url
        url ?: return //TODO nice!!
        repo.getEpisodeByEnclosureUrlSync(url)?.let {
            notifyDependingOnTheStatus(requestInfo, service, it, status, progress)
        }
    }


    private fun notifyDependingOnTheStatus(requestInfo: RequestInfo, service: Service, episode: Episode, status: Int, progress: Int) {
        if (status != STATUS_REMOVED) {
            service.startForeground(requestInfo.id.toInt(), getNotification(episode, progress))
            return
        }
        if (status == STATUS_REMOVED) {
            notifManager.removeNotification(requestInfo.id.toInt())
            return
        }

    }

    private fun getNotification(episode: Episode, progress: Int): Notification? =
            if (progress < DOWNLOAD_COMPLETED) {
                notificationCreator.createProgressNotification(episode, progress)
            } else {
                notificationCreator.createCompleteNotification(episode)
            }

    override fun notifySpaceUnavailable(requestInfo: RequestInfo) {
        repo.getEpisodeByEnclosureUrlSync(requestInfo.url)?.let {
            val id = requestInfo.id.toInt()
            notifManager.notify(id, notificationCreator.getNoSpaceNotification(it))
        }
    }

    override fun notifySpaceUnavailable(title: String) {
        repo.getEpisodeByUriSync(title)?.let {
            notifManager.notify(GENERIC_NOTIFICATION, notificationCreator.getNoSpaceNotification(it))
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
            notifManager.sendBoroadcast(notificationCreator.makeOnDeleteEpisode(it))
        }
    }

}