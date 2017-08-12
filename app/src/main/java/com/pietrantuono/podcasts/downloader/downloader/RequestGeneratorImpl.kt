package com.pietrantuono.podcasts.downloader.downloader

import android.webkit.URLUtil
import com.pietrantuono.podcasts.settings.PreferencesManager
import com.rometools.rome.feed.synd.SyndEnclosure
import com.tonyodev.fetch.request.Request
import javax.inject.Inject


class RequestGeneratorImpl @Inject constructor(private val preferencemanager: PreferencesManager) : RequestGenerator {

    override fun createRequest(enclosure: SyndEnclosure): Request? =
            enclosure.url?.let {
                val dir = preferencemanager.downloadDir
                val filname = URLUtil.guessFileName(it, null, null)
                Request(it, dir, filname)
            }

}

