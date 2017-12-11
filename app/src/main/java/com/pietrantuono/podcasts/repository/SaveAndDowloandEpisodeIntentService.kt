package com.pietrantuono.podcasts.repository

import android.app.IntentService
import android.content.Intent
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import models.pojos.Episode
import models.pojos.Podcast
import models.pojos.PodcastRealm
import repo.repository.PodcastRepo
import javax.inject.Inject

class SaveAndDowloandEpisodeIntentService : IntentService("SaveAndDowloandEpisodeIntentService") {
    @Inject lateinit var api: SinglePodcastApi
    @Inject lateinit var crashlyticsWrapper: CrashlyticsWrapper
    @Inject lateinit var logger: DebugLogger
    @Inject lateinit var downloader: Downloader
    @Inject lateinit var repo: PodcastRepo

    companion object {
        val TAG = "SaveAndDowloandEpisodeIntentService"
        val TRACK_ID = "track_id"
        val URL = "url"
    }

    override fun onHandleIntent(intent: Intent?) {
        intent ?: return
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        logger.debug(TAG, "onHandleIntent")
        var podcast: Podcast? = getPodcast(intent) ?: return
        val episodes = getEpisodes(intent) ?: return
        podcast?.episodes = episodes
        repo.savePodcastSync(podcast as? PodcastRealm)
        downloader.downloadIfAppropriate(podcast)
    }

    private fun getEpisodes(intent: Intent): MutableList<Episode>? {
        logger.debug(TAG, "getEpisodes")
        val url = intent.getStringExtra(URL)
        if (url.isNullOrBlank()) {
            return null
        }
        try {
            return api.getFeedSync(url).execute().body().episodes
        } catch (exception: Exception) {
            crashlyticsWrapper.logException(exception)
        }
        return null
    }

    private fun getPodcast(intent: Intent): Podcast? {
        val id = intent.getIntExtra(TRACK_ID, -1)
        return repo.getPodcastByIdSync(id)
    }

}