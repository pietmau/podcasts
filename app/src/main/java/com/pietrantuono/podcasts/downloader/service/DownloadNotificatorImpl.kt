package com.pietrantuono.podcasts.downloader.service

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.request.RequestInfo

class DownloadNotificatorImpl(
        private val context: Context,
        private val repo: EpisodesRepository) : DownloadNotificator {

    companion object {
        const val DOWNLOAD_COMPLETED: Int = 100
    }

    private val notifManager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun notifyProgress(requestInfo: RequestInfo?, progress: Int) {
        val url = requestInfo?.url
        url ?: return
        repo.getEpisodeByEnclosureUrlSync(url)?.let {
            notifManager.notify(requestInfo.id.toInt(), getNotification(it, progress))
        }
    }

    private fun getNotification(episode: Episode, progress: Int): Notification? {
        if (progress < DOWNLOAD_COMPLETED) {
            return createProgressNotification(episode, progress)
        } else {
            return createCompleteNotification(episode)
        }
    }

    private fun createCompleteNotification(episode: Episode): Notification? {
        val text = getText(episode)
        return NotificationCompat
                .Builder(context)
                .setContentTitle(context.getString(R.string.download_completed))
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
    }

    private fun createProgressNotification(episode: Episode, progress: Int): Notification {
        val text = getText(episode)
        return NotificationCompat
                .Builder(context)
                .setContentTitle(context.getString(R.string.downloading))
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(DOWNLOAD_COMPLETED, progress, false)
                .build()
    }

    private fun getText(episode: Episode) = episode.title ?: context.getString(R.string.title_not_available)

}