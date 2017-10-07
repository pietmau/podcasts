package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.graphics.Bitmap
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v7.app.NotificationCompat
import android.view.View
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import java.util.*
import javax.inject.Inject


class IconCache @Inject constructor(private val imageLoader: SimpleImageLoader) {
    val map: WeakHashMap<String?, Bitmap?> = WeakHashMap<String?, Bitmap?>()

    fun cacheImage(key: String?, value: Bitmap?) {
        map.clear()
        map.put(key, value)
    }

    fun getCachedImage(key: String) = map[key]

    private fun fetchBitmapFromURLAsync(notificationManager: NotificationManagerCompat, bitmapUrl: String?,
                                        builder: NotificationCompat.Builder) {
        imageLoader.loadImage(bitmapUrl, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                cacheImage(imageUri, loadedImage)
                builder.setLargeIcon(loadedImage)
                notificationManager.notify(NotificationsConstants.NOTIFICATION_ID, builder.build())
            }
        })
    }

    fun setOrGetIcon(notificationManager: NotificationManagerCompat, description: MediaDescriptionCompat,
                             notificationBuilder: NotificationCompat.Builder) {
        val iconUri = description?.iconUri?.toString()
        if (iconUri != null) {
            if (getCachedImage(iconUri) == null) {
                fetchBitmapFromURLAsync(notificationManager, iconUri, notificationBuilder)
            } else {
                notificationBuilder.setLargeIcon(getCachedImage(iconUri))
            }
        }
    }

}