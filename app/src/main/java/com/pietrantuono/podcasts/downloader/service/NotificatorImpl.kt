package com.pietrantuono.podcasts.downloader.service

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.PodcastEpisode

class NotificatorImpl(private val context: Context) : Notificator {

    companion object {
        const val DOWNLOAD_COMPLETED: Int = 100
    }

    private val notifManager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun notifyProgress(episode: PodcastEpisode?, id: Long, progress: Int) {
        if (episode == null) {
            return
        }
        val notification = getNotification(episode, progress)
        val notificationId: Int = calculateNotificationId(id)
        notifManager.notify(notificationId, notification)
    }

    private fun getNotification(podcastEpisode: PodcastEpisode, progress: Int): Notification? {
        if (progress < DOWNLOAD_COMPLETED) {
            return createProgressNotification(podcastEpisode, progress)
        } else {
            return createCompleteNotification(podcastEpisode)
        }
    }

    private fun createCompleteNotification(episode: PodcastEpisode): Notification? {
        val text = getText(episode)
        return NotificationCompat
                .Builder(context)
                .setContentTitle(context.getString(R.string.download_completed))
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
    }

    private fun createProgressNotification(episode: PodcastEpisode, progress: Int): Notification {
        val text = getText(episode)
        return NotificationCompat
                .Builder(context)
                .setContentTitle(context.getString(R.string.downloading))
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(DOWNLOAD_COMPLETED, progress, false)
                .build()
    }

    private fun getText(episode: PodcastEpisode) = episode.title ?: context.getString(R.string.title_not_available)

    private fun calculateNotificationId(id: Long): Int {
        val diff = id - (Int.MAX_VALUE.toLong())
        if (diff >= 0) {
            return calculateNotificationId(diff)
        } else {
            return id.toInt()
        }
    }
}