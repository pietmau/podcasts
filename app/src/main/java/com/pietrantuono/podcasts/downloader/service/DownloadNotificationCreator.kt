package com.pietrantuono.podcasts.downloader.service

import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.tonyodev.fetch.request.RequestInfo
import models.pojos.Episode
import javax.inject.Inject

class DownloadNotificationCreator//TODO Delete
@Inject constructor(private val context: Context) {

    companion object {
        val ACTION_DOWNLOAD_UPDATE = "com.podcasts.downloadupdate"
        val ACTION_DOWNLOAD_COMPLETE = "com.podcasts.downloadcomplete"
        val ACTION_DELETE = "com.podcasts.delete"
        val EXTRA_URL = "url"
        val EXTRA_PROGRESS = "progress"
        val EXTRA_FILE_SIZE = "file_size"
    }

    fun createCompleteNotification(episode: Episode) =
            NotificationCompat
                    .Builder(context)
                    .setContentTitle(context.getString(R.string.download_completed))
                    .setContentText(getTitle(episode))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build()

    private fun getTitle(episode: Episode) = episode.title ?: context.getString(R.string.title_not_available)

    fun createProgressNotification(episode: Episode, progress: Int) =
            NotificationCompat
                    .Builder(context)
                    .setContentTitle(context.getString(R.string.downloading))
                    .setContentText(getTitle(episode))
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setProgress(DOWNLOAD_COMPLETED, progress, false)
                    .build()

    fun getNoSpaceNotification(episode: Episode) =
            NotificationCompat
                    .Builder(context)
                    .setContentTitle(context.getString(R.string.no_space))
                    .setContentText(getTitle(episode))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build()


    fun makeUpdateBroadcast(info: RequestInfo, progress: Int, fileSize: Long) =
            Intent().apply {
                action = ACTION_DOWNLOAD_UPDATE
                putExtra(EXTRA_URL, info.url)
                putExtra(EXTRA_PROGRESS, progress)
                        .putExtra(EXTRA_FILE_SIZE, fileSize)
            }

    fun makeOnDownloadCompletedBroadcast(requestInfo: RequestInfo) =
            Intent().apply {
                action = ACTION_DOWNLOAD_COMPLETE
                putExtra(EXTRA_URL, requestInfo.url)
            }

    fun makeOnDeleteEpisode(episode: Episode) =
            Intent().apply {
                action = ACTION_DELETE
                putExtra(EXTRA_URL, episode.link)
            }

}