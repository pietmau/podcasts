package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.graphics.Bitmap
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v7.app.NotificationCompat
import android.view.View
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import java.util.*
import javax.inject.Inject

class IconCache @Inject constructor(private val imageLoader: SimpleImageLoader, private val logger: DebugLogger) {
    val map: HashMap<String?, Bitmap?> = HashMap<String?, Bitmap?>()
    val TAG = this.toString()

    fun cacheImage(key: String?, value: Bitmap?) {
        map.clear()
        map.put(key, value)
    }

    fun getCachedImage(key: String): Bitmap? {
        val result = map[key]
        logger.debug(TAG, "getCachedImage -> url = $key | image = $result")
        return result
    }

    private fun fetchBitmapFromURLAsync(notificationManager: NotificationManagerCompat, bitmapUrl: String?,
                                        builder: NotificationCompat.Builder) {
        logger.debug(TAG, "fetchBitmapFromURLAsync -> url = $bitmapUrl | manager = $notificationManager | builder = $builder")
        imageLoader.loadImage(bitmapUrl, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                logger.debug(TAG, "onLoadingComplete. -> Bitmap = " + loadedImage)
                cacheImage(imageUri, loadedImage)
                builder.setLargeIcon(loadedImage)
                notificationManager.notify(NotificationsConstants.NOTIFICATION_ID, builder.build())
            }

            override fun onLoadingStarted(imageUri: String?, view: View?) {
                logger.debug(TAG, "onLoadingStarted")
            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {
                logger.debug(TAG, "onLoadingCancelled")
            }

            override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                logger.debug(TAG, "onLoadingFailed | reason = ${failReason?.cause?.message}")
            }
        })
    }

    fun setOrGetIcon(notificationManager: NotificationManagerCompat, description: MediaDescriptionCompat,
                     notificationBuilder: NotificationCompat.Builder) {
        val iconUri = description?.iconUri?.toString()
        if (iconUri != null) {
            logger.debug(TAG, "setOrGetIcon, -> URI = " + iconUri)
            if (getCachedImage(iconUri) == null) {
                fetchBitmapFromURLAsync(notificationManager, iconUri, notificationBuilder)
            } else {
                notificationBuilder.setLargeIcon(getCachedImage(iconUri))
            }
        }
    }

}