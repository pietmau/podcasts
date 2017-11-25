package com.pietrantuono.podcasts.repository

import android.app.IntentService
import android.content.Intent
import com.pietrantuono.podcasts.CrashlyticsWrapper

import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import io.realm.Realm
import pojos.Episode
import pojos.PodcastRealm
import javax.inject.Inject

class SaveAndDowloandEpisodeIntentService : IntentService("SaveAndDowloandEpisodeIntentService") {

    @Inject lateinit var api: SinglePodcastApi
    @Inject lateinit var crashlyticsWrapper: CrashlyticsWrapper
    @Inject lateinit var logger: DebugLogger
    @Inject lateinit var downloader: Downloader

    companion object {
        val TAG = "SaveAndDowloandEpisodeIntentService"
        val TRACK_ID = "track_id"
        val URL = "url"
    }

    override fun onHandleIntent(intent: Intent?) {
        (application as App).applicationComponent?.with()?.inject(this)
        logger.debug(TAG, "onHandleIntent")
        var podcast: PodcastRealm? = getPodcast(intent!!) ?: return
        val episodes = getEpisodes(intent) ?: return
        Realm.getDefaultInstance().executeTransaction {
            podcast?.episodes = episodes
            podcast = it.copyToRealmOrUpdate(podcast)
            logger.debug(TAG, "executeTransaction")
        }
        logger.debug(TAG, "downloadIfAppropriate")
        downloader.downloadIfAppropriate(podcast)
    }

    private fun getEpisodes(intent: Intent): List<Episode>? {
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

    private fun getPodcast(intent: Intent): PodcastRealm? {
        val intExtra = intent.getIntExtra(TRACK_ID, -1)
        val podcastRealm = Realm.getDefaultInstance().use { realm ->
            realm.copyFromRealm(realm.where(PodcastRealm::class.java)
                    .equalTo("trackId", intExtra)
                    .findFirst())
        }
        logger.debug(TAG, "podcastRealm = " + podcastRealm)
        return podcastRealm
    }

}