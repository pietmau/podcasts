package com.pietrantuono.podcasts.repository

import android.app.IntentService
import android.content.Intent
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.providers.PodcastRealm
import io.realm.Realm
import javax.inject.Inject

class SaveEpisodeIntentService : IntentService("SaveEpisodeIntentService") {
    private lateinit var realm: Realm
    @Inject lateinit var api: SinglePodcastApi
    @Inject lateinit var logger: CrashlyticsWrapper

    companion object {
        val TRACK_ID = "track_id"
        val URL = "url"
    }

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        realm = Realm.getDefaultInstance()
        val podcast: PodcastRealm? = getPodcast(intent!!) ?: return
        val episodes = getEpisodes(intent) ?: return
        realm.executeTransaction {
            podcast?.episodes = episodes
            realm.copyToRealmOrUpdate(podcast)
        }
        realm.close()
    }

    private fun getEpisodes(intent: Intent): List<PodcastEpisode>? {
        val url = intent.getStringExtra(URL)
        if (url.isNullOrBlank()) {
            return null
        }
        val call = api.getFeedSync(url)
        try {
            return call.execute().body().episodes
        } catch (exception: Exception) {
            logger.logException(exception)
        }
        return null
    }

    private fun getPodcast(intent: Intent): PodcastRealm? {
        return realm.where(PodcastRealm::class.java)
                .equalTo("trackId", intent.getIntExtra(TRACK_ID, -1))
                .findFirst()
    }

}