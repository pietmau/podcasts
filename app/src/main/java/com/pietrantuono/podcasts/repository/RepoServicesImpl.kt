package com.pietrantuono.podcasts.repository

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.repository.repository.RepoServices
import io.realm.Realm

class RepoServicesImpl(
        private val context: Context,
        private val realm: Realm,
        private val logger: DebugLogger) : RepoServices {

    companion object {
        val TAG: String? = "RepoServicesImpl"
    }

    override fun getAndDowloadEpisodes(podcast: Podcast, subscribe: Boolean) {
        if (subscribe) {
            startService(podcast)
        }
    }

    private fun startService(singlePodcast: Podcast) {
        logger.debug(TAG, "startService " + singlePodcast)
        val intent = Intent(context, SaveAndDowloandEpisodeIntentService::class.java)
        intent.putExtra(SaveAndDowloandEpisodeIntentService.TRACK_ID, singlePodcast.trackId)
        intent.putExtra(SaveAndDowloandEpisodeIntentService.URL, singlePodcast.feedUrl)
        context.startService(intent)
    }

}

