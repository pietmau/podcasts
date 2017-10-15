package com.pietrantuono.podcasts.repository

import android.app.IntentService
import android.content.Intent
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.providers.PodcastRealm
import io.realm.Realm
import javax.inject.Inject

class SaveAndDowloandEpisodeIntentService : IntentService("SaveAndDowloandEpisodeIntentService") {
    @Inject lateinit var realm: Realm
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
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        val podcast: PodcastRealm? = getPodcast(intent!!) ?: return
        val episodes = getEpisodes(intent) ?: return
        realm.executeTransaction {
            podcast?.episodes = episodes
            realm.copyToRealmOrUpdate(podcast)
        }
        downloader.downloadIfAppropriate(podcast)
        realm.close()
    }

    private fun getEpisodes(intent: Intent): List<Episode>? {
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
        return realm.where(PodcastRealm::class.java)
                .equalTo("trackId", intent.getIntExtra(TRACK_ID, -1))
                .findFirst()
    }

}