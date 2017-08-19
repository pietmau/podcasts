package com.pietrantuono.podcasts.downloader.service

import com.pietrantuono.podcasts.apis.Episode

interface Notificator {
    fun notifyProgress(episode: Episode?, id: Long, progress: Int)

}