package com.pietrantuono.podcasts.repository

import android.app.IntentService
import android.content.Intent
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.App
import io.realm.Realm
import javax.inject.Inject

class SaveEpisodeIntentService : IntentService("SaveEpisodeIntentService") {
    @Inject lateinit var realm: Realm
    @Inject lateinit var api: SinglePodcastApi
    companion object {
        val TRACK_ID = "track_id"
        val URL = "url"
    }

    override fun onHandleIntent(intent: Intent?) {
        (application as App).applicationComponent?.inject(this@SaveEpisodeIntentService)
    }


}