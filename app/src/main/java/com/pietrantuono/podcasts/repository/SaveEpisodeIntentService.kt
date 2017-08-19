package com.pietrantuono.podcasts.repository

import android.app.IntentService
import android.content.Intent
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.providers.PodcastRealm
import io.realm.Realm
import javax.inject.Inject

class SaveEpisodeIntentService : IntentService("SaveEpisodeIntentService") {
    @Inject lateinit var realm: Realm
    @Inject lateinit var api: SinglePodcastApi
    @Inject lateinit var crashlyticsWrapper: CrashlyticsWrapper
    @Inject lateinit var logger: DebugLogger

    companion object {
        val TAG = "SaveEpisodeIntentService"
        val TRACK_ID = "track_id"
        val URL = "url"
    }

    override fun onHandleIntent(intent: Intent?) {
        (application as App).applicationComponent?.inject(this)
        logger.debug(TAG,"onHandleIntent")
        val podcast: PodcastRealm? = getPodcast(intent!!) ?: return
        logger.debug(TAG,"got podcast")
        val episodes = getEpisodes(intent) ?: return
        logger.debug(TAG,"got episodes")
        realm.executeTransaction {
            podcast?.episodes = episodes
            realm.copyToRealmOrUpdate(podcast)
        }
        realm.close()
    }

    private fun getEpisodes(intent: Intent): List<Episode>? {
        val url = intent.getStringExtra(URL)
        if (url.isNullOrBlank()) {
            return null
        }
        val call = api.getFeedSync(url)
        try {
            return call.execute().body().episodes
        } catch (exception: Exception) {
            crashlyticsWrapper.logException(exception)
        }
        return null
    }

    private fun getPodcast(intent: Intent): PodcastRealm? {
        return realm.where(PodcastRealm::class.java)
                .equalTo("trackId", intent.getIntExtra(TRACK_ID, -1))
                .findFirst()
    }

}