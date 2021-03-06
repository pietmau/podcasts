package com.pietrantuono.podcasts.repository

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.application.DebugLogger
import models.pojos.Podcast
import repo.repository.RepoServices

class RepoServicesImpl(
        private val context: Context,
        private val logger: DebugLogger) : RepoServices {

    companion object {
        val TAG: String? = "RepoServicesImpl"
    }

    override fun getAndDowloadEpisodes(podcast: Podcast?, subscribe: Boolean?) {
        if (subscribe == true && podcast != null) {
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

